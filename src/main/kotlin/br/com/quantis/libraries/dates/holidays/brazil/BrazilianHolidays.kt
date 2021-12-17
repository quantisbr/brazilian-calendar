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
@file:JvmName("BrazilianHolidays")
package br.com.quantis.libraries.dates.holidays.brazil

import br.com.quantis.libraries.dates.DateRange
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.MonthDay

typealias Year = Int

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
 * Converts informed year to Easter date
 * @receiver Year of Easter. Used to calculate the day and month of Easter
 * @return Date of Easter
 */
fun Year.toEasterDate(): LocalDate {
    val year = this
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

    return LocalDate.of(year, month, day)
}

/**
 * Converts informed year to Carnival date
 * @receiver Year of Carnival. Used to calculate the day and month of Carnival
 * @return Date of Carnival
 */
fun Year.toCarnivalDate(): LocalDate = this.toEasterDate().minusDays(47)

/**
 * Converts informed year to Good Friday date
 * @receiver Year of Good Friday. Used to calculate the day and month of Good Friday
 * @return Date of Good Friday
 */
fun Year.toGoodFridayDate(): LocalDate = this.toEasterDate().minusDays(2)

/**
 * Converts informed year to Corpus Christi date
 * @receiver Year of Corpus Christi. Used to calculate the day and month of Corpus Christi
 * @return Date of Corpus Christi
 */
fun Year.toCorpusChristiDate(): LocalDate = this.toEasterDate().plusDays(60)

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
 * Check if the date entered is a bank public holiday. Includes all fixed and mobile holidays.
 * __Note: Carnival, Carnival Monday and Corpus Christi are not holidays, but traditionally banks close on these days, according to data from [Febraban](https://feriadosbancarios.febraban.org.br/)__
 * @receiver Date to be verified
 * @return Returns true if informed date is a bank public holiday
 */
fun LocalDate.isBankHoliday(): Boolean {
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
 * @param includeSaturday Normally Saturday is not a working day, but there are situations that it should be considered. In these cases set the parameter to true
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
 * Check if the date entered is a bank business day.
 * @receiver Date to be verified
 * @return Returns true if informed date is a bank business day
 */
fun LocalDate.isBankBusinessDay(): Boolean {
    if (this.dayOfWeek == SATURDAY)
        return false

    if (this.dayOfWeek == SUNDAY)
        return false

    if (this.isBankHoliday())
        return false

    return true
}

/**
 * Count the number of business days in the range
 * @receiver Date range
 * @param includeSaturday Normally Saturday is not a working day, but there are situations that it should be considered. In these cases set the parameter to true
 * @return Returns the number of business day
 */
fun DateRange.countBusinessDays(includeSaturday: Boolean = false) = this.count { date -> date.isBusinessDay(includeSaturday) }

/**
 * Count the number of bank business days in the range
 * @receiver Date range
 * @return Returns the number of bank business day
 */
fun DateRange.countBankBusinessDays() = this.count { date -> date.isBankBusinessDay() }
