package br.com.quantis.libraries.calendar.brazil

import br.com.quantis.libraries.calendar.religious.toCarnivalDate
import br.com.quantis.libraries.calendar.religious.toCorpusChristiDate
import br.com.quantis.libraries.calendar.religious.toGoodFridayDate
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class BrazilianCalendarTest {

    @Test
    fun `Test if Universal Day is a national holiday`() {
        val date = LocalDate.of(2022, 1, 1)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Tiradentes is a national holiday`() {
        val date = LocalDate.of(2016, 4, 21)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Labour Day is a national holiday`() {
        val date = LocalDate.of(2020, 5, 1)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Independence Day is a national holiday`() {
        val date = LocalDate.of(2020, 9, 7)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Our Lady of Conception Aparecida Day is a national holiday`() {
        val date = LocalDate.of(2015, 10, 12)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if All Souls Day is a national holiday`() {
        val date = LocalDate.of(2021, 11, 2)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Proclamation of the Republic Day is a national holiday`() {
        val date = LocalDate.of(2021, 11, 15)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Christmas Day is a national holiday`() {
        val date = LocalDate.of(1990, 12, 25)
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Good Friday is a national holiday`() {
        val date = 1962.toGoodFridayDate()
        assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if any national holiday is bank public holiday`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val nationalHoliday = mockk<LocalDate>()
            every { nationalHoliday.isNationalHoliday() } returns true

            assertTrue(nationalHoliday.isBankingHoliday())

            verify(exactly = 1) { nationalHoliday.isNationalHoliday() }
        }
    }

    @Test
    fun `Test if Carnival Monday is bank public holiday`() {
        val carnivalMonday =  2022.toCarnivalDate().minusDays(1)
        assertTrue(carnivalMonday.isBankingHoliday())
    }

    @Test
    fun `Test if Carnival is bank public holiday`() {
        val carnival =  2023.toCarnivalDate()
        assertTrue(carnival.isBankingHoliday())
    }

    @Test
    fun `Test if Corpus Christi day is bank public holiday`() {
        val corpusChristis =  2024.toCorpusChristiDate()
        assertTrue(corpusChristis.isBankingHoliday())
    }

    @Test
    fun `Test any public holiday is not a business day`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val nationalHoliday = mockk<LocalDate>()
            every { nationalHoliday.dayOfWeek } returns DayOfWeek.THURSDAY
            every { nationalHoliday.isNationalHoliday() } returns true

            assertFalse(nationalHoliday.isBusinessDay())

            verify(exactly = 1) { nationalHoliday.isNationalHoliday() }
        }
    }

    @Test
    fun `Test any Sunday is not a business day`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val sunday = mockk<LocalDate>()
            every { sunday.isNationalHoliday() } returns false
            every { sunday.dayOfWeek } returns DayOfWeek.SUNDAY

            assertFalse(sunday.isBusinessDay())

            verify { sunday.dayOfWeek }
            verify(exactly = 0) { sunday.isNationalHoliday() }
        }
    }

    @Test
    fun `Test any Saturday is not a business day when not included`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val saturday = mockk<LocalDate>()
            every { saturday.isNationalHoliday() } returns false
            every { saturday.dayOfWeek } returns DayOfWeek.SATURDAY

            assertFalse(saturday.isBusinessDay())

            verify { saturday.dayOfWeek }
            verify(exactly = 0) { saturday.isNationalHoliday() }
        }
    }

    @Test
    fun `Test any Saturday is a business day when included`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val saturday = mockk<LocalDate>()
            every { saturday.isNationalHoliday() } returns false
            every { saturday.dayOfWeek } returns DayOfWeek.SATURDAY

            assertTrue(saturday.isBusinessDay(includeSaturday = true))

            verify { saturday.dayOfWeek }
        }
    }

    @Test
    fun `Test any bank public holiday is not a business day`() {
        mockkStatic(LocalDate::isBankingHoliday) {
            val bankHoliday = mockk<LocalDate>()
            every { bankHoliday.dayOfWeek } returns DayOfWeek.FRIDAY
            every { bankHoliday.isBankingHoliday() } returns true

            assertFalse(bankHoliday.isBankingBusinessDay())

            verify(exactly = 1) { bankHoliday.isBankingHoliday() }
        }
    }

    @Test
    fun `Test any Saturday is not a banking business day`() {
        mockkStatic(LocalDate::isBankingHoliday) {
            val saturday = mockk<LocalDate>()
            every { saturday.isBankingHoliday() } returns false
            every { saturday.dayOfWeek } returns DayOfWeek.SATURDAY

            assertFalse(saturday.isBankingBusinessDay())

            verify { saturday.dayOfWeek }
            verify(exactly = 0) { saturday.isBankingHoliday() }
        }
    }

    @Test
    fun `Test any Sunday is not a banking business day`() {
        mockkStatic(LocalDate::isBankingHoliday) {
            val sunday = mockk<LocalDate>()
            every { sunday.isBankingHoliday() } returns false
            every { sunday.dayOfWeek } returns DayOfWeek.SUNDAY

            assertFalse(sunday.isBankingBusinessDay())

            verify { sunday.dayOfWeek }
            verify(exactly = 0) { sunday.isBankingHoliday() }
        }
    }

    @Test
    fun `Should count the number of business day for the informed date range`() {
        val start = LocalDate.now()
        val end = LocalDate.now().plusDays(30)

        val expectedCountBusinessDays = start.datesUntil(end.plusDays(1)).filter { date -> date.isBusinessDay() }.count()
        assertEquals(expectedCountBusinessDays, countBusinessDays(start, end))
    }

    @Test
    fun `Should count the number of business day for the range`() {
        val start = LocalDate.now()
        val end = LocalDate.now().plusDays(30)

        val expectedCountBusinessDays = start.datesUntil(end.plusDays(1)).filter { date -> date.isBusinessDay() }.count()

        val range = start..end
        assertEquals(expectedCountBusinessDays, range.countBusinessDays())
    }

    @Test
    fun `Should count the number of business day for the informed date range including Saturday`() {
        val start = LocalDate.now()
        val end = LocalDate.now().plusDays(30)

        val expectedCountBusinessDays = start.datesUntil(end.plusDays(1)).filter { date -> date.isBusinessDay(true) }.count()
        assertEquals(expectedCountBusinessDays, countBusinessDays(start, end, true))
    }

    @Test
    fun `Should count the number of business day for the range including Saturday`() {
        val start = LocalDate.now().minusWeeks(6)!!
        val end = LocalDate.now().plusMonths(11)!!

        val expectedCountBusinessDays = start.datesUntil(end.plusDays(1)).filter { date -> date.isBusinessDay(true) }.count()

        val range = start..end
        assertEquals(expectedCountBusinessDays, range.countBusinessDays(true))
    }

    @Test
    fun `Should count the number of banking business day for the informed date range`() {
        val start = LocalDate.now().minusWeeks(8)!!
        val end = LocalDate.now().plusMonths(6)!!

        val expectedBankCountBusinessDays = start.datesUntil(end.plusDays(1)).filter { date -> date.isBankingBusinessDay() }.count()

        assertEquals(expectedBankCountBusinessDays, countBankingBusinessDays(start, end))
    }

    @Test
    fun `Should count the number of banking business day for the range`() {
        val start = LocalDate.now().minusWeeks(8)!!
        val end = LocalDate.now().plusMonths(6)!!

        val expectedBankingCountBusinessDays = start.datesUntil(end.plusDays(1)).filter { date -> date.isBankingBusinessDay() }.count()

        val range = start..end
        assertEquals(expectedBankingCountBusinessDays, range.countBankingBusinessDays())
    }

    @Test
    fun `Should count the number of calendar days in the range of informed dates`() {
        val start = LocalDate.of(2020, 1, 1)
        val end = LocalDate.of(2020, 1, 31)
        val expectedCalendarDays = ChronoUnit.DAYS.between(start, end)
        assertEquals(expectedCalendarDays, calendarDays(start, end))
    }

    @Test
    fun `Should count the number of calendar days for the range`() {
        val start = LocalDate.of(2020, 1, 1)
        val end = LocalDate.of(2020, 1, 31)
        val range = start..end
        val expectedCalendarDays = ChronoUnit.DAYS.between(start, end)
        assertEquals(expectedCalendarDays, range.calendarDays())
    }

    @Test
    fun `Should return the next business day for informed date`() {
        val date = LocalDate.of(2021, 12, 24)

        var expected = date.plusDays(1)
        while (!expected.isBusinessDay())
            expected = expected.plusDays(1)

        assertEquals(expected, date.nextBusinessDay())
    }

    @Test
    fun `Should return the next business day for informed date including Saturday`() {
        val date = LocalDate.of(2021, 12, 17)

        var expected = date.plusDays(1)
        while (!expected.isBusinessDay(true))
            expected = expected.plusDays(1)

        assertEquals(expected, date.nextBusinessDay(true))
    }

    @Test
    fun `Should return the next banking business day for informed date`() {
        val friday = 2021.toCarnivalDate().minusDays(4)

        var expected = friday.plusDays(1)
        while (!expected.isBankingBusinessDay())
            expected = expected.plusDays(1)

        assertEquals(expected, friday.nextBankingBusinessDay())
    }
}