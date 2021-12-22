/**
 * Copyright 2021 Quantis All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:JvmName("BrazilianCalendar")
package br.com.quantis.libraries.calendar.brazil

import br.com.quantis.libraries.calendar.religious.toCarnivalDate
import br.com.quantis.libraries.calendar.religious.toCorpusChristiDate
import br.com.quantis.libraries.calendar.religious.toGoodFridayDate
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.LocalDate
import java.time.MonthDay
import java.time.temporal.ChronoUnit

private val fixedHolidays = setOf<MonthDay>(
    MonthDay.of(1, 1), //Universal Day
    MonthDay.of(4, 21), //Tiradentes
    MonthDay.of(5, 1), //Labour Day
    MonthDay.of(9, 7), //Independence Day
    MonthDay.of(10, 12), //Our Lady of Conception Aparecida Day
    MonthDay.of(11, 2), //All Souls Day
    MonthDay.of(11, 15), //Proclamation of the Republic Day
    MonthDay.of(12, 25) //Christmas Day
)

/**
 * Check if the date entered is a national holiday. Includes all fixed and mobile holidays.
 * __Note: Carnival and Corpus Christi are not holidays. They are optional day off__
 * @receiver Date to be verified
 * @return Returns true if informed date is a national public holiday
 */
fun LocalDate.isNationalHoliday(): Boolean {
    val monthDay = MonthDay.of(this.month, this.dayOfMonth)
    return fixedHolidays.contains(monthDay) || this == this.year.toGoodFridayDate()
}

/**
 * Check if the date entered is a banking holiday. Includes all fixed and mobile holidays.
 * __Note: Carnival, Carnival Monday and Corpus Christi are not holidays, but traditionally banks close on these days, according to data from [Febraban](https://feriadosbancarios.febraban.org.br/)__
 * @receiver Date to be verified
 * @return Returns true if informed date is a banking holiday
 */
fun LocalDate.isBankingHoliday(): Boolean {
    if (this.isNationalHoliday())
        return true

    val carnival = this.year.toCarnivalDate()
    if (this == carnival)
        return true

    val carnivalMonday = carnival.minusDays(1)
    if (this == carnivalMonday)
        return true

    val corpusChristis = this.year.toCorpusChristiDate()
    return this == corpusChristis
}

/**
 * Check if the date entered is a business day.
 * @receiver Date to be verified
 * @param includeSaturday Normally Saturday is not a business day, but there are situations that it should be considered. In these cases set the parameter to true
 * @return Returns true if informed date is a business day
 */
fun LocalDate.isBusinessDay(includeSaturday: Boolean = false): Boolean {
    if (!includeSaturday && this.dayOfWeek == SATURDAY)
        return false

    if (this.dayOfWeek == SUNDAY)
        return false

    if (this.isNationalHoliday())
        return false

    return true
}

/**
 * Check if the date entered is a banking business day.
 * @receiver Date to be verified
 * @return Returns true if informed date is a bank business day
 */
fun LocalDate.isBankingBusinessDay(): Boolean {
    if (this.dayOfWeek == SATURDAY)
        return false

    if (this.dayOfWeek == SUNDAY)
        return false

    if (this.isBankingHoliday())
        return false

    return true
}

/**
 * Count the number of business days in the informed date range
 * @param start Start date (inclusive)
 * @param endInclusive End date (inclusive)
 * @param includeSaturday Normally Saturday is not a business day, but there are situations that it should be considered. In these cases set the parameter to true
 * @return Returns the number of business days in the informed date range
 */
fun countBusinessDays(start: LocalDate, endInclusive: LocalDate, includeSaturday: Boolean = false): Long
{
    val allDates = allDates(start, endInclusive)
    return allDates.filter { date -> date.isBusinessDay(includeSaturday) }.count()
}

/**
 * Count the number of business days in the range
 * @receiver Date range
 * @param includeSaturday Normally Saturday is not a business day, but there are situations that it should be considered. In these cases set the parameter to true
 * @return Returns the number of business day
 */
fun ClosedRange<LocalDate>.countBusinessDays(includeSaturday: Boolean = false): Long
{
    val allDates = allDates(this.start, this.endInclusive)
    return allDates.filter { date -> date.isBusinessDay(includeSaturday) }.count()
}

private fun allDates(start: LocalDate, endInclusive: LocalDate) = start.datesUntil(endInclusive.plusDays(1))

/**
 * Count the number of banking business days in the informed date range
 * @param start Start date (inclusive)
 * @param endInclusive End date (inclusive)
 * @return Returns the number of banking business days in the informed date range
 */
fun countBankingBusinessDays(start: LocalDate, endInclusive: LocalDate): Long {
    val allDates = allDates(start, endInclusive)
    return allDates.filter { date -> date.isBankingBusinessDay() }.count()
}


/**
 * Count the number of banking business days in the range
 * @receiver Date range
 * @return Returns the number of banking business day
 */
fun ClosedRange<LocalDate>.countBankingBusinessDays(): Long {
    val allDates = allDates(this.start, this.endInclusive)
    return allDates.filter { date -> date.isBankingBusinessDay() }.count()
}

/**
 * Count the number of calendar days in the range
 * @receiver Date range
 * @return Returns the number of calendar days in the range
 */
fun ClosedRange<LocalDate>.calendarDays(): Long = calendarDays(start, endInclusive)

/**
 * Count the number of calendar days in the range of informed dates
 * @param start Start date (inclusive)
 * @param endInclusive End date (inclusive)
 * @return Returns the number of calendar days in the of informed dates
 */
fun calendarDays(start: LocalDate, endInclusive: LocalDate): Long = ChronoUnit.DAYS.between(start, endInclusive)


/**
 * Get the next business day
 * @receiver Date
 * @param includeSaturday Normally Saturday is not a business day, but there are situations that it should be considered. In these cases set the parameter to true
 * @return Returns the next business day
 */
fun LocalDate.nextBusinessDay(includeSaturday: Boolean = false): LocalDate {
    var result = this.plusDays(1)
    while (!result.isBusinessDay(includeSaturday))
        result = result.plusDays(1)
    return result
}

/**
 * Get the next banking business day
 * @receiver Date
 * @return Returns the next business day
 */
fun LocalDate.nextBankingBusinessDay(): LocalDate {
    var result = this.plusDays(1)
    while (!result.isBankingBusinessDay())
        result = result.plusDays(1)
    return result
}
