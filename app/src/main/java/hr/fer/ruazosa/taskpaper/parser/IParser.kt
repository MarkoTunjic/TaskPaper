package hr.fer.ruazosa.taskpaper.parser

interface IParser<T> {

    fun parse(text: String)

    fun getLastParsingResult(): ParserResult<T>
}