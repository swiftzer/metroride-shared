@file:Suppress("UnusedPrivateProperty")

package net.swiftzer.metroride.shared.bigdecimal

public expect class BigDecimal : Comparable<BigDecimal> {
    public constructor(value: String)
    public constructor(unscaledVal: Long, scale: Int)

    public fun abs(): BigDecimal
    public operator fun plus(augend: BigDecimal): BigDecimal
    public operator fun minus(subtrahend: BigDecimal): BigDecimal
    public operator fun times(multiplicand: BigDecimal): BigDecimal
    public operator fun div(divisor: BigDecimal): BigDecimal
    public fun div(divisor: BigDecimal, scale: Int, roundingMode: RoundingMode): BigDecimal
    public operator fun unaryMinus(): BigDecimal
    public fun setScale(newScale: Int, roundingMode: RoundingMode): BigDecimal

    /**
     * Due to different libraries are used across different platforms, the behavior is different.
     *
     * For example, `BigDecimal("5.0") == BigDecimal("5")`
     * - JVM: `false`
     * - JS: `true`
     *
     * Use [compareTo] to do value comparison.
     */
    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}
