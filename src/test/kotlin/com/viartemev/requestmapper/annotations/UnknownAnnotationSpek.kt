package com.viartemev.requestmapper.annotations

import org.amshove.kluent.shouldBeEmpty
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertTrue

object UnknownAnnotationSpek : Spek({
    describe("UnknownAnnotation") {
        on("values") {
            it("should return an empty list") {
                UnknownAnnotation.values().shouldBeEmpty()
            }
            it("should return the same empty list") {
                assertTrue(UnknownAnnotation.values() == UnknownAnnotation.values())
            }
        }
    }
})
