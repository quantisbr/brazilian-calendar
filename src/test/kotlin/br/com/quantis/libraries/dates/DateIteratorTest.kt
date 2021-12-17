package br.com.quantis.libraries.dates

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class DateIteratorTest {

    @Test
    fun `Test if the 'endInclusive' parameter is greater than or equal to the 'start' parameter`() {
        val start = LocalDate.now()
        assertDoesNotThrow { DateIterator(start, start) }
        assertDoesNotThrow { DateIterator(start, start.plusDays(1))}
        val endInclusive = start.minusDays(1)
        val exception = assertThrows<IllegalArgumentException> { DateIterator(start, endInclusive) }
        val expectedMessage =
            "The 'endInclusive' parameter must be greater than or equal to the start parameter. Parameters: start = '$start' - endInclusive = '$endInclusive'"
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `Test iterable`() {
        val start = LocalDate.now()
        val end = start.plusDays(2)

        val iterator = DateIterator(start, end)

        assertTrue(iterator.hasNext())
        assertEquals(start.plusDays(1), iterator.next())

        assertTrue(iterator.hasNext())
        assertEquals(start.plusDays(2), iterator.next())

        assertFalse(iterator.hasNext())

        assertThrows<NoSuchElementException> { iterator.next() }
    }
}
