package hr.fer.ruazosa.taskpaper.models

import hr.fer.ruazosa.taskpaper.parser.TaskPaperParser
import org.junit.Assert.*
import org.junit.Test

class MainTaskTest {
    @Test
    fun testConvertToString_WhenPassingStringWithPriority_ShouldReturnSameValueAsParserInput() {
        val parser= TaskPaperParser()
        val text="Groceries:\n\t-honey @priority(2)\n\t-milk"
        parser.parse(text)
        assertEquals(text,parser.getLastParsingResult().result.toString())
    }

    @Test
    fun testConvertToString_WhenPassingStringWithDoneA_ShouldReturnSameValueAsParserInput() {
        val parser= TaskPaperParser()
        val text="Groceries:\n\t-honey @done\n\t-milk"
        parser.parse(text)
        assertEquals(text,parser.getLastParsingResult().result.toString())
    }

    @Test
    fun testConvertToString_WhenPassingStringWithDoneAndPriority_ShouldReturnSameValueAsParserInput() {
        val parser= TaskPaperParser()
        val text="Groceries:\n\t-honey @done @priority(2)\n\t-milk"
        parser.parse(text)
        assertEquals(text,parser.getLastParsingResult().result.toString())
    }
}
