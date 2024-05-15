package net.swiftzer.metroride.shared.bigdecimal

import java.math.RoundingMode as JvmRoundingMode

public inline val RoundingMode.jvmValue: JvmRoundingMode
    get() = when (this) {
        RoundingMode.CEILING -> JvmRoundingMode.CEILING
        RoundingMode.DOWN -> JvmRoundingMode.DOWN
        RoundingMode.FLOOR -> JvmRoundingMode.FLOOR
        RoundingMode.HALF_DOWN -> JvmRoundingMode.HALF_DOWN
        RoundingMode.HALF_EVEN -> JvmRoundingMode.HALF_EVEN
        RoundingMode.HALF_UP -> JvmRoundingMode.HALF_UP
        RoundingMode.UP -> JvmRoundingMode.UP
    }
