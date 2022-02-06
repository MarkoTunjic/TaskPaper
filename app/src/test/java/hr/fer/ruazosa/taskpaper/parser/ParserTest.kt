package hr.fer.ruazosa.taskpaper.parser

import org.junit.Test
import org.junit.Assert.*

class ParserTest {
    @Test
    fun testParse_WhenPriorityHasNoValue_ShouldThrowParserException() {
        val parser=TaskPaperParser()
        val text="Groceries:\n\t-honey\n\t-milk @priority"
        assertThrows(ParserException::class.java){ parser.parse(text) }
    }

    @Test
    fun testParse_WhenAddingValueToDoneParameter_ShouldThrowParserException() {
        val parser=TaskPaperParser()
        val text="Groceries:\n\t-honey\n\t-milk @done(2)"
        assertThrows(ParserException::class.java){ parser.parse(text) }
    }

    @Test
    fun testGetLastParsingResult_AfterParsingTextWhereSubtaskHasDone_TaskWithDoneMustHaveCompletedSetToTrue() {
        val parser=TaskPaperParser()
        val text="Groceries:\n\t-honey\n\t-milk @done"
        parser.parse(text)
        assertEquals("Groceries",parser.getLastParsingResult().result.name)
        assertEquals(2,parser.getLastParsingResult().result.subtasks.size)
        assertEquals(0,parser.getLastParsingResult().result.subtasks[1].priority)
        assertEquals(true,parser.getLastParsingResult().result.subtasks[1].completed)
    }

    @Test
    fun testGetLastParsingResult_AfterParsingTextWhereSubtaskHasPriority_TaskWithPriorityMustHavePriorityDefined() {
        val parser=TaskPaperParser()
        val text="Groceries:\n\t-honey\n\t-milk @priority(2)"
        parser.parse(text)
        assertEquals("Groceries",parser.getLastParsingResult().result.name)
        assertEquals(2,parser.getLastParsingResult().result.subtasks.size)
        assertEquals(2,parser.getLastParsingResult().result.subtasks[1].priority)
        assertEquals(false,parser.getLastParsingResult().result.subtasks[0].completed)
    }

    @Test
    fun testGetLastParsingResult_AfterParsingTextWhereSubtaskHasPriorityAndDone_TaskWithPriorityAndDoneMustHavePriorityDefinedAndDoneSetToTrue() {
        val parser=TaskPaperParser()
        val text="Groceries:\n\t-honey @done @priority(2)\n\t-milk"
        parser.parse(text)
        assertEquals("Groceries",parser.getLastParsingResult().result.name)
        assertEquals(2,parser.getLastParsingResult().result.subtasks.size)
        assertEquals(2,parser.getLastParsingResult().result.subtasks[0].priority)
        assertEquals(true,parser.getLastParsingResult().result.subtasks[0].completed)
    }
}