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

class DateIterator(val start: LocalDate, val endInclusive: LocalDate): Iterator<LocalDate> {

    private var actualValue = start

    init {
        require(endInclusive >= start) { "The 'endInclusive' parameter must be greater than or equal to the start parameter. Parameters: start = '$start' - endInclusive = '$endInclusive'" }
    }

    override fun hasNext() = actualValue < endInclusive

    override fun next(): LocalDate {
        if (hasNext()) {
            actualValue = actualValue.plusDays(1)
            return actualValue
        } else
            throw NoSuchElementException()
    }

    override fun toString(): String {
        return "DateIterator(start=$start, endInclusive=$endInclusive, actualValue=$actualValue)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateIterator

        if (start != other.start) return false
        if (endInclusive != other.endInclusive) return false
        if (actualValue != other.actualValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + endInclusive.hashCode()
        result = 31 * result + actualValue.hashCode()
        return result
    }
}
