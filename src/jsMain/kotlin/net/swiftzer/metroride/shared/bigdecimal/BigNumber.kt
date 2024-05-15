@file:Suppress("UnusedPrivateProperty")

package net.swiftzer.metroride.shared.bigdecimal

@JsModule("bignumber.js")
@JsNonModule
public external class BigNumber {

    public constructor(n: String)
    public constructor(n: Number)

    public val c: Array<Number>?
    public val e: Number?
    public val s: Int?

    public fun absoluteValue(): BigNumber
    public fun plus(n: BigNumber): BigNumber
    public fun minus(n: BigNumber): BigNumber
    public fun multipliedBy(n: BigNumber): BigNumber
    public fun dividedBy(n: BigNumber): BigNumber
    public fun negated(): BigNumber
    public fun shiftedBy(n: Int): BigNumber
    public fun decimalPlaces(): Number
    public fun decimalPlaces(dp: Number, rm: Number): BigNumber
    public fun comparedTo(n: BigNumber): Int?
    public fun isEqualTo(n: BigNumber): Boolean
    public fun toNumber(): Number

    public companion object {
        public val ROUND_UP: Number
        public val ROUND_DOWN: Number
        public val ROUND_CEIL: Number
        public val ROUND_FLOOR: Number
        public val ROUND_HALF_UP: Number
        public val ROUND_HALF_DOWN: Number
        public val ROUND_HALF_EVEN: Number
        public val ROUND_HALF_CEIL: Number
        public val ROUND_HALF_FLOOR: Number
    }
}
