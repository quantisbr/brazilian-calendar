package br.com.quantis.libraries.dates.holidays.brazil

import br.com.quantis.libraries.dates.rangeTo
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate

class BrazilianHolidaysTest {

    @Test
    fun `Test convert year to easter date`() {
        val year = 2019
        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * l) / 451
        val month = (h + l - 7 * m + 114) / 31
        val day = 1+ (h + l - 7 * m + 114)% 31

        val pascoa = LocalDate.of(year, month, day)
        assertEquals(pascoa, year.toEasterDate())
    }

    @Test
    fun `Test convert year to carnival date`() {
        val year = 2020
        val easter = year.toEasterDate()
        val carnival = easter.minusDays(47)
        assertEquals(carnival, year.toCarnivalDate())
    }

    @Test
    fun `Test convert year to Good Friday date`() {
        val year = 2017
        val easter = year.toEasterDate()
        val goodFriday = easter.minusDays(2)
        assertEquals(goodFriday, year.toGoodFridayDate())
    }

    @Test
    fun `Test convert year to Corpus Christi date`() {
        val year = 2021
        val easter = year.toEasterDate()
        val corpusChristiDate = easter.plusDays(60)
        assertEquals(corpusChristiDate, year.toCorpusChristiDate())
    }

    @Test
    fun `Test if Universal Day is a national holiday`() {
        val date = LocalDate.of(2022, 1, 1)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Tiradentes is a national holiday`() {
        val date = LocalDate.of(2016, 4, 21)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Labour Day is a national holiday`() {
        val date = LocalDate.of(2020, 5, 1)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Independence Day is a national holiday`() {
        val date = LocalDate.of(2020, 9, 7)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Our Lady of Conception Aparecida Day is a national holiday`() {
        val date = LocalDate.of(2015, 10, 12)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if All Souls Day is a national holiday`() {
        val date = LocalDate.of(2021, 11, 2)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Proclamation of the Republic Day is a national holiday`() {
        val date = LocalDate.of(2021, 11, 15)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Christmas Day is a national holiday`() {
        val date = LocalDate.of(1990, 12, 25)
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if Good Friday is a national holiday`() {
        val date = 1962.toGoodFridayDate()
        Assertions.assertTrue(date.isNationalHoliday())
    }

    @Test
    fun `Test if any national holiday is bank public holiday`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val nationalHoliday = mockk<LocalDate>()
            every { nationalHoliday.isNationalHoliday() } returns true

            Assertions.assertTrue(nationalHoliday.isBankHoliday())

            verify(exactly = 1) { nationalHoliday.isNationalHoliday() }
        }
    }

    @Test
    fun `Test if Carnival Monday is bank public holiday`() {
        val carnivalMonday =  2022.toCarnivalDate().minusDays(1)
        Assertions.assertTrue(carnivalMonday.isBankHoliday())
    }

    @Test
    fun `Test if Carnival is bank public holiday`() {
        val carnival =  2023.toCarnivalDate()
        Assertions.assertTrue(carnival.isBankHoliday())
    }

    @Test
    fun `Test if Corpus Christi day is bank public holiday`() {
        val corpusChristis =  2024.toCorpusChristiDate()
        Assertions.assertTrue(corpusChristis.isBankHoliday())
    }

    @Test
    fun `Test any public holiday is not a business day`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val nationalHoliday = mockk<LocalDate>()
            every { nationalHoliday.dayOfWeek } returns DayOfWeek.THURSDAY
            every { nationalHoliday.isNationalHoliday() } returns true

            Assertions.assertFalse(nationalHoliday.isBusinessDay())

            verify(exactly = 1) { nationalHoliday.isNationalHoliday() }
        }
    }

    @Test
    fun `Test any Sunday is not a business day`() {
        mockkStatic(LocalDate::isNationalHoliday) {
            val sunday = mockk<LocalDate>()
            every { sunday.isNationalHoliday() } returns false
            every { sunday.dayOfWeek } returns DayOfWeek.SUNDAY

            Assertions.assertFalse(sunday.isBusinessDay())

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

            Assertions.assertFalse(saturday.isBusinessDay())

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

            Assertions.assertTrue(saturday.isBusinessDay(includeSaturday = true))

            verify { saturday.dayOfWeek }
        }
    }

    @Test
    fun `Test any bank public holiday is not a business day`() {
        mockkStatic(LocalDate::isBankHoliday) {
            val bankHoliday = mockk<LocalDate>()
            every { bankHoliday.dayOfWeek } returns DayOfWeek.FRIDAY
            every { bankHoliday.isBankHoliday() } returns true

            Assertions.assertFalse(bankHoliday.isBankBusinessDay())

            verify(exactly = 1) { bankHoliday.isBankHoliday() }
        }
    }

    @Test
    fun `Test any Saturday is not a bank business day`() {
        mockkStatic(LocalDate::isBankHoliday) {
            val saturday = mockk<LocalDate>()
            every { saturday.isBankHoliday() } returns false
            every { saturday.dayOfWeek } returns DayOfWeek.SATURDAY

            Assertions.assertFalse(saturday.isBankBusinessDay())

            verify { saturday.dayOfWeek }
            verify(exactly = 0) { saturday.isBankHoliday() }
        }
    }

    @Test
    fun `Test any Sunday is not a bank business day`() {
        mockkStatic(LocalDate::isBankHoliday) {
            val sunday = mockk<LocalDate>()
            every { sunday.isBankHoliday() } returns false
            every { sunday.dayOfWeek } returns DayOfWeek.SUNDAY

            Assertions.assertFalse(sunday.isBankBusinessDay())

            verify { sunday.dayOfWeek }
            verify(exactly = 0) { sunday.isBankHoliday() }
        }
    }

    @Test
    fun `Should count the number of business day for the range`() {
        val start = LocalDate.now().minusWeeks(12)!!
        val end = LocalDate.now().plusMonths(7)!!
        val range = start..end
        val expectedCountBusinessDays = range.count { date -> date.isBusinessDay() }
        assertEquals(expectedCountBusinessDays, range.countBusinessDays())
    }

    @Test
    fun `Should count the number of business day for the range included Saturday`() {
        val start = LocalDate.now().minusWeeks(6)!!
        val end = LocalDate.now().plusMonths(11)!!
        val range = start..end
        val expectedCountBusinessDays = range.count { date -> date.isBusinessDay(true) }
        assertEquals(expectedCountBusinessDays, range.countBusinessDays(true))
    }

    @Test
    fun `Should count the number of bank business day for the range`() {
        val start = LocalDate.now().minusWeeks(12)!!
        val end = LocalDate.now().plusMonths(7)!!
        val range = start..end
        val expectedCountBusinessDays = range.count { date -> date.isBankBusinessDay() }
        assertEquals(expectedCountBusinessDays, range.countBankBusinessDays())
    }
}