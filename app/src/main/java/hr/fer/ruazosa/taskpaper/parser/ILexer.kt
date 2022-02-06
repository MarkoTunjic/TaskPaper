package hr.fer.ruazosa.taskpaper.parser

interface ILexer {
    var state:LexerState
    fun nextToken():LexingToken
}