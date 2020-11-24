package com.viartemev.requestmapper.annotations.spring.classmapping

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ClassUnknownMappingSpek : Spek({
    describe("ClassUnknownMapping") {
        context("extract properties") {
            it("should return empty class mapping") {
                ClassUnknownAnnotation().fetchClassMapping() shouldBeEqualTo listOf()
            }
            it("should return empty bound type") {
                ClassUnknownAnnotation().fetchBoundMappingFromClass() shouldBeEqualTo emptySet()
            }
        }
    }
})
