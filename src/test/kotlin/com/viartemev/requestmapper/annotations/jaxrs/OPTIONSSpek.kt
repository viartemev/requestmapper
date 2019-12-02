package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object OPTIONSSpek : Spek({
    describe("OPTIONS") {
        context("extractMethod") {
            it("should return OPTIONS") {
                val annotation = mock<PsiAnnotation> {}
                Options(annotation).extractMethod() shouldBeEqualTo "OPTIONS"
            }
        }
    }
})
