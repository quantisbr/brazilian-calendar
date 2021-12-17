package br.com.quantis.libraries.dates

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DateRangeKtTest {
    @Test
    fun `Test operator rangeTo for LocalDate`() {
        val start = LocalDate.now()
        val end = start.plusDays(1)
        val expected = DateRange(start, end)
        assertEquals(expected, start.rangeTo(end))

        assertEquals(expected, start..end)
    }
}