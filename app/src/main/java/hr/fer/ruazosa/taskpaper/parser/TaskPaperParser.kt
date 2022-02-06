package hr.fer.ruazosa.taskpaper.parser

import hr.fer.ruazosa.taskpaper.models.MainTask
import hr.fer.ruazosa.taskpaper.models.SubTask
import java.lang.NumberFormatException

class TaskPaperParser : IParser<MainTask> {

    private var lastParsedTask: MainTask? = null

    override fun getLastParsingResult(): ParserResult<MainTask> {
        if (lastParsedTask == null)
            throw ParserException("Nothing was parsed")
        return ParserResult(lastParsedTask!!)
    }

    override fun parse(text: String) {
        val lexer: ILexer
        lexer = TaskPaperLexer(text)
        var taskName: String? = null
        var subtaskName: String? = null
        var priorityHappened = false
        var completed = false
        var priority = 0
        var leftBracket = false
        var subTasks: MutableList<SubTask>? = null
        var tokenCounter = 0
        var token = lexer.nextToken()
        while (token.tokenType != TokenType.EOF) {
            if (tokenCounter > 0)
                if (taskName == null)
                    throw ParserException("Task name was not found")

            when (token.tokenType) {
                TokenType.TEXT -> {
                    when (lexer.state) {
                        LexerState.NORMAL -> {
                            if (taskName == null)
                                taskName = token.value
                            else if (subtaskName == null)
                                subtaskName = token.value
                            else
                                throw ParserException("Assigning name of task/subtask twice")
                        }
                        LexerState.AT -> {
                            if (token.value.lowercase().trim() == "priority")
                                priorityHappened = true
                            else if (token.value.lowercase().trim() == "done"){
                                completed = true
                                lexer.state=LexerState.NORMAL
                            }
                            else {
                                if (priorityHappened && leftBracket) {
                                    try {
                                        priority = Integer.parseInt(token.value)
                                    } catch (ex: NumberFormatException) {
                                        throw ParserException("Invalid priority")
                                    }
                                } else
                                    throw ParserException("Invalid state")
                            }
                        }
                    }
                }
                TokenType.DASH -> {
                    if(priorityHappened)
                        throw ParserException("No argument for priority found")
                    if (subTasks == null)
                        subTasks = mutableListOf()
                    else {
                        subTasks!!.add(SubTask(subtaskName!!.trim(), priority, completed))
                    }
                    subtaskName = null
                    priority = 0
                    completed = false
                }
                TokenType.DOUBLE_DOT -> {
                    if (taskName == null)
                        throw ParserException("Task name was not found")
                }
                TokenType.AT -> {
                    lexer.state = LexerState.AT
                }
                TokenType.LEFT_BRACKET -> {
                    leftBracket = true
                }
                TokenType.RIGHT_BRACKET -> {
                    if (!leftBracket)
                        throw ParserException("No left bracket found before right bracket")
                    leftBracket = false
                    lexer.state = LexerState.NORMAL
                    priorityHappened=false
                }
            }
            tokenCounter++
            token = lexer.nextToken()
        }
        if(priorityHappened)
            throw ParserException("No argument for priority found")
        if (subTasks != null) {
            subTasks!!.add(SubTask(subtaskName!!.trim(), priority, completed))
            lastParsedTask = MainTask(taskName!!, subTasks!!)
        }else{
            lastParsedTask = MainTask(taskName!!, mutableListOf())
        }
    }
}