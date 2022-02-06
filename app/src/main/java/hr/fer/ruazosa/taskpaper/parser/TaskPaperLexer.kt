package hr.fer.ruazosa.taskpaper.parser

import java.util.function.Predicate

class TaskPaperLexer(text: String) : ILexer {
    private val data = text.toCharArray()
    private var currentIndex = 0
    private var eof = false
    override var state = LexerState.NORMAL

    override fun nextToken(): LexingToken {
        skipAllWhitespaces()
        if (eof)
            throw LexerException("EOF. No more tokens")

        if (currentIndex >= data.size) {
            eof = true
            return LexingToken(TokenType.EOF, "EOF")
        }


        val token: LexingToken = when (data[currentIndex]) {
            '-' -> LexingToken(TokenType.DASH, data[currentIndex++].toString())
            '@' -> LexingToken(TokenType.AT, data[currentIndex++].toString())
            ':' -> LexingToken(TokenType.DOUBLE_DOT, data[currentIndex++].toString())
            '(' -> LexingToken(TokenType.LEFT_BRACKET, data[currentIndex++].toString())
            ')' -> LexingToken(TokenType.RIGHT_BRACKET, data[currentIndex++].toString())
            else -> {
                val value = StringBuilder()
                if (state == LexerState.NORMAL)
                    addWhileTrue(value) { letter ->
                        letter != '@' && letter != '-' && letter != ':'
                    }
                else
                    addWhileTrue(value) { letter ->
                        letter != '@' && letter != ':' && letter != '(' && letter != ')'&&letter != '-'
                    }
                LexingToken(TokenType.TEXT, value.toString())
            }
        }
        return token
    }

    private fun addWhileTrue(value: StringBuilder, rule: Predicate<Char>) {
        while (currentIndex < data.size && rule.test(data[currentIndex])) {
            value.append(data[currentIndex++])
        }
    }
    private fun skipAllWhitespaces() {
        while (currentIndex < data.size && Character.isWhitespace(data[currentIndex])) {
            currentIndex++
        }
    }
}