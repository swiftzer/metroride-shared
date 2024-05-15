@file:Suppress("MatchingDeclarationName")

package net.swiftzer.metroride.shared.bigdecimal

public actual class BigDecimal : Comparable<BigDecimal> {
    private val value: BigNumber

    public actual constructor(value: String) {
        this.value = BigNumber(value)
    }

    public actual constructor(unscaledVal: Long, scale: Int) {
        value = BigNumber(unscaledVal).shiftedBy(-scale)
    }

    public constructor(bigNumber: BigNumber) {
        value = bigNumber
    }

    public actual fun abs(): BigDecimal = BigDecimal(value.absoluteValue())

    public actual operator fun plus(augend: BigDecimal): BigDecimal = BigDecimal(value.plus(augend.value))

    public actual operator fun minus(subtrahend: BigDecimal): BigDecimal = BigDecimal(value.minus(subtrahend.value))

    public actual operator fun times(multiplicand: BigDecimal): BigDecimal =
        BigDecimal(value.multipliedBy(multiplicand.value))

    public actual operator fun div(divisor: BigDecimal): BigDecimal = BigDecimal(value.dividedBy(divisor.value))

    public actual fun div(
        divisor: BigDecimal,
        scale: Int,
        roundingMode: RoundingMode,
    ): BigDecimal = BigDecimal((this / divisor).value.decimalPlaces(scale, roundingMode.jsValue))

    public actual operator fun unaryMinus(): BigDecimal = BigDecimal(value.negated())

    public actual fun setScale(
        newScale: Int,
        roundingMode: RoundingMode,
    ): BigDecimal = BigDecimal(value.decimalPlaces(newScale, roundingMode.jsValue))

    override fun compareTo(other: BigDecimal): Int = requireNotNull(value.comparedTo(other.value)) {
        "this or other is NaN"
    }

    override fun toString(): String = value.toString().uppercase()

    actual override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as BigDecimal

        return value.isEqualTo(other.value)
    }

    actual override fun hashCode(): Int {
        return value.hashCode()
    }
}

public val RoundingMode.jsValue: Number
    get() = when (this) {
        RoundingMode.CEILING -> BigNumber.ROUND_CEIL
        RoundingMode.DOWN -> BigNumber.ROUND_DOWN
        RoundingMode.FLOOR -> BigNumber.ROUND_FLOOR
        RoundingMode.HALF_DOWN -> BigNumber.ROUND_HALF_DOWN
        RoundingMode.HALF_EVEN -> BigNumber.ROUND_HALF_EVEN
        RoundingMode.HALF_UP -> BigNumber.ROUND_HALF_UP
        RoundingMode.UP -> BigNumber.ROUND_UP
    }
