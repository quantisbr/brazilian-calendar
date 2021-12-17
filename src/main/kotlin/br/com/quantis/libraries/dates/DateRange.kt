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
