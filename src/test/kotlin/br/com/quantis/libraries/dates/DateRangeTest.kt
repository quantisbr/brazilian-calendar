package br.com.quantis.libraries.dates

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class DateRangeTest {

    @Test
    fun `Test get iterantor`() {
        val start = LocalDate.now()
        val end = start.plusDays(5)
        val expected = DateIterator(start, end)

        val range = DateRange(start, end)
        assertEquals(expected, range.iterator())
    }
}