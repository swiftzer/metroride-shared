@file:Suppress("MatchingDeclarationName")

package net.swiftzer.metroride.shared.bigdecimal

import java.math.BigDecimal as JvmBigDecimal

public actual class BigDecimal : Comparable<BigDecimal> {
    private val value: JvmBigDecimal

    public actual constructor(value: String) {
        this.value = JvmBigDecimal(value)
    }

    public actual constructor(unscaledVal: Long, scale: Int) {
        value = JvmBigDecimal.valueOf(unscaledVal, scale)
    }

    public constructor(bigDecimal: JvmBigDecimal) {
        value = bigDecimal
    }

    public actual fun abs(): BigDecimal = BigDecimal(value.abs())

    public actual operator fun plus(augend: BigDecimal): BigDecimal = BigDecimal(value.add(augend.value))

    public actual operator fun minus(subtrahend: BigDecimal): BigDecimal = BigDecimal(value.subtract(subtrahend.value))

    public actual operator fun times(multiplicand: BigDecimal): BigDecimal = BigDecimal(value.multiply(multiplicand.value))

    public actual operator fun div(divisor: BigDecimal): BigDecimal = BigDecimal(value.divide(divisor.value))

    public actual fun div(
        divisor: BigDecimal,
        scale: Int,
        roundingMode: RoundingMode,
    ): BigDecimal = BigDecimal(value.divide(divisor.value, scale, roundingMode.jvmValue))

    public actual operator fun unaryMinus(): BigDecimal = BigDecimal(value.negate())

    public actual fun setScale(
        newScale: Int,
        roundingMode: RoundingMode,
    ): BigDecimal = BigDecimal(value.setScale(newScale, roundingMode.jvmValue))

    override fun compareTo(other: BigDecimal): Int = value.compareTo(other.value)

    override fun toString(): String = value.toString()
    actual override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BigDecimal

        return value == other.value
    }

    actual override fun hashCode(): Int {
        return value.hashCode()
    }
}
