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
package br.com.quantis.libraries.dates

import java.time.LocalDate

/**
 * Implementing a date range
 */
class DateRange(
    override val start: LocalDate,
    override val endInclusive: LocalDate
): ClosedRange<LocalDate>, Iterable<LocalDate> {

    override fun iterator(): Iterator<LocalDate> = DateIterator(start, endInclusive)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateRange

        if (start != other.start) return false
        if (endInclusive != other.endInclusive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + endInclusive.hashCode()
        return result
    }

    override fun toString(): String {
        return "DateRange(start=$start, endInclusive=$endInclusive)"
    }
}

operator fun LocalDate.rangeTo(that: LocalDate) = DateRange(this, that)
