package com.viartemev.requestmapper.annotations

import org.amshove.kluent.shouldBeEmpty
import org.junit.jupiter.api.Assertions.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object UnknownAnnotationSpek : Spek({
    describe("UnknownAnnotation") {
        context("values") {
            it("should return an empty list") {
                UnknownAnnotation.values().shouldBeEmpty()
            }
            it("should return the same empty list") {
                assertTrue(UnknownAnnotation.values() == UnknownAnnotation.values())
            }
        }
    }
})
