package net.swiftzer.metroride.shared.bigdecimal

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json

class BigDecimalSerializerTest : FunSpec(
    {
        test("descriptor") {
            BigDecimalSerializer.descriptor.serialName shouldBe "BigDecimal"
        }

        test("serialize") {
            val bigDecimal = BigDecimal("5.7621728e-12")
            Json.encodeToString(
                serializer = BigDecimalSerializer,
                value = bigDecimal,
            ) shouldBe """"5.7621728E-12""""
        }

        test("deserialize") {
            Json.decodeFromString(
                deserializer = BigDecimalSerializer,
                string = """"5.7621728E-12"""",
            ) shouldBe BigDecimal("5.7621728E-12")
        }
    },
)
