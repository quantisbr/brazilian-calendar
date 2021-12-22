package br.com.quantis.libraries.calendar.religious

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class MobileReligiousEventsTest {

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
}