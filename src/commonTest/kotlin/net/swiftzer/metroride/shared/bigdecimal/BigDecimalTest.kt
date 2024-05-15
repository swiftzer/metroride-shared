package net.swiftzer.metroride.shared.bigdecimal

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.shouldBe

class BigDecimalTest : FunSpec(
    {
        test("constructor with unscaledVal and scale") {
            BigDecimal(unscaledVal = 987654321L, scale = 4) shouldBe BigDecimal("98765.4321")
        }

        test("abs") {
            BigDecimal("123.45678").abs() shouldBe BigDecimal("123.45678")
            BigDecimal("-123.45678").abs() shouldBe BigDecimal("123.45678")
        }

        test("plus") {
            (BigDecimal("4.0") + BigDecimal("2.0")) shouldBe BigDecimal("6.0")
            (BigDecimal("4") + BigDecimal("-7.1")) shouldBe BigDecimal("-3.1")
            (BigDecimal("12.4623") + BigDecimal("5342.33")) shouldBe BigDecimal("5354.7923")
            (BigDecimal("112255.6473762401") + BigDecimal("625578631883141131859950104709.402446232383239610277")) shouldBe
                BigDecimal("6.25578631883141131859950216965049822472483239610277e+29")
        }

        test("minus") {
            (BigDecimal("-5.6") - BigDecimal("-0.00000000023")) shouldBe BigDecimal("-5.59999999977")
            (BigDecimal("2") - BigDecimal("0")) shouldBe BigDecimal("2")
            (BigDecimal("23063425385264062114.4182185521") - BigDecimal("919024.6882123634392645395934284705089")) shouldBe
                BigDecimal("23063425385263143089.7300061886607354604065715294911")
            (BigDecimal("4.6379") - BigDecimal("0.0010851657258206953367908525441350095857171922501796819281707")) shouldBe
                BigDecimal("4.6368148342741793046632091474558649904142828077498203180718293")
        }

        test("times") {
            (BigDecimal("99999") * BigDecimal("1")) shouldBe BigDecimal("99999")
            (BigDecimal("0.3341376") * BigDecimal("-8")) shouldBe BigDecimal("-2.6731008")
            (BigDecimal("-0.0000000000000000148") * BigDecimal("-389336")) shouldBe BigDecimal("5.7621728e-12")
            (BigDecimal("-2107306488.021444213082463895093082025238625832167567") * BigDecimal("-0.000000016561760020640192077800")) shouldBe
                BigDecimal("34.900704344549244590017380682576128567920825673075388993583448795500712600")
        }

        test("div") {
            (BigDecimal("-2.8") / BigDecimal("4")) shouldBe BigDecimal("-0.7")
            (BigDecimal("999.5") / BigDecimal("1000")) shouldBe BigDecimal("0.9995")
        }

        test("div with scale") {
            BigDecimal("-2.8").div(BigDecimal("3"), 6, RoundingMode.HALF_UP) shouldBe BigDecimal("-0.933333")
            BigDecimal("-10.47").div(BigDecimal("-0.00000690102"), 2, RoundingMode.HALF_EVEN) shouldBe BigDecimal("1517167.03")
        }

        test("unaryMinus") {
            -BigDecimal("-2.8") shouldBe BigDecimal("2.8")
            -BigDecimal("2.8") shouldBe BigDecimal("-2.8")
        }

        test("compareTo") {
            BigDecimal("4.56").compareTo(BigDecimal("2.4")).shouldBeGreaterThan(0)
            BigDecimal("-4.56").compareTo(BigDecimal("2.4")).shouldBeLessThan(0)
            BigDecimal("2343.23").compareTo(BigDecimal("2343.2300")).shouldBeZero()
            BigDecimal("-0").compareTo(BigDecimal("0")).shouldBeZero()
        }

        test("equals") {
            (BigDecimal("1234.5678") == BigDecimal("987.43")).shouldBeFalse()
            (BigDecimal("1234.5678") == BigDecimal("1234.5678")).shouldBeTrue()
            (BigDecimal("-0") == BigDecimal("0")).shouldBeTrue()
        }

        test("toString") {
            BigDecimal("0").toString() shouldBe "0"
            BigDecimal("-12.333334").toString() shouldBe "-12.333334"
            BigDecimal("5.7621728e-12").toString() shouldBe "5.7621728E-12"
        }

        withData(
            nameFn = { "setScale [input = ${it.input}, scale = ${it.scale}]" },
            ts = sequenceOf(
                SetScale(
                    input = "5.5",
                    scale = 0,
                    up = "6",
                    down = "5",
                    ceiling = "6",
                    floor = "5",
                    halfUp = "6",
                    halfDown = "5",
                    halfEven = "6",
                ),
                SetScale(
                    input = "2.5",
                    scale = 0,
                    up = "3",
                    down = "2",
                    ceiling = "3",
                    floor = "2",
                    halfUp = "3",
                    halfDown = "2",
                    halfEven = "2",
                ),
                SetScale(
                    input = "1.6",
                    scale = 0,
                    up = "2",
                    down = "1",
                    ceiling = "2",
                    floor = "1",
                    halfUp = "2",
                    halfDown = "2",
                    halfEven = "2",
                ),
                SetScale(
                    input = "1.1",
                    scale = 0,
                    up = "2",
                    down = "1",
                    ceiling = "2",
                    floor = "1",
                    halfUp = "1",
                    halfDown = "1",
                    halfEven = "1",
                ),
                SetScale(
                    input = "1.0",
                    scale = 0,
                    up = "1",
                    down = "1",
                    ceiling = "1",
                    floor = "1",
                    halfUp = "1",
                    halfDown = "1",
                    halfEven = "1",
                ),
                SetScale(
                    input = "-1.0",
                    scale = 0,
                    up = "-1",
                    down = "-1",
                    ceiling = "-1",
                    floor = "-1",
                    halfUp = "-1",
                    halfDown = "-1",
                    halfEven = "-1",
                ),
                SetScale(
                    input = "-1.1",
                    scale = 0,
                    up = "-2",
                    down = "-1",
                    ceiling = "-1",
                    floor = "-2",
                    halfUp = "-1",
                    halfDown = "-1",
                    halfEven = "-1",
                ),
                SetScale(
                    input = "-1.6",
                    scale = 0,
                    up = "-2",
                    down = "-1",
                    ceiling = "-1",
                    floor = "-2",
                    halfUp = "-2",
                    halfDown = "-2",
                    halfEven = "-2",
                ),
                SetScale(
                    input = "-2.5",
                    scale = 0,
                    up = "-3",
                    down = "-2",
                    ceiling = "-2",
                    floor = "-3",
                    halfUp = "-3",
                    halfDown = "-2",
                    halfEven = "-2",
                ),
                SetScale(
                    input = "-5.5",
                    scale = 0,
                    up = "-6",
                    down = "-5",
                    ceiling = "-5",
                    floor = "-6",
                    halfUp = "-6",
                    halfDown = "-5",
                    halfEven = "-6",
                ),
            ),
            test = {
                BigDecimal(it.input).setScale(it.scale, RoundingMode.UP) shouldBe BigDecimal(it.up)
                BigDecimal(it.input).setScale(it.scale, RoundingMode.DOWN) shouldBe BigDecimal(it.down)
                BigDecimal(it.input).setScale(it.scale, RoundingMode.CEILING) shouldBe BigDecimal(it.ceiling)
                BigDecimal(it.input).setScale(it.scale, RoundingMode.FLOOR) shouldBe BigDecimal(it.floor)
                BigDecimal(it.input).setScale(it.scale, RoundingMode.HALF_UP) shouldBe BigDecimal(it.halfUp)
                BigDecimal(it.input).setScale(it.scale, RoundingMode.HALF_DOWN) shouldBe BigDecimal(it.halfDown)
                BigDecimal(it.input).setScale(it.scale, RoundingMode.HALF_DOWN) shouldBe BigDecimal(it.halfDown)
            },
        )
    },
)

/**
 * [Sample data](https://docs.oracle.com/javase/8/docs/api/java/math/RoundingMode.html)
 */
data class SetScale(
    val input: String,
    val scale: Int,
    val up: String,
    val down: String,
    val ceiling: String,
    val floor: String,
    val halfUp: String,
    val halfDown: String,
    val halfEven: String,
)
