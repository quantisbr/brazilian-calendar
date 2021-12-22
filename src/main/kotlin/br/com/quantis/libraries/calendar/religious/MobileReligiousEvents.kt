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
@file:JvmName("MobileReligiousEvents")
package br.com.quantis.libraries.calendar.religious

import java.time.LocalDate

typealias Year = Int


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
