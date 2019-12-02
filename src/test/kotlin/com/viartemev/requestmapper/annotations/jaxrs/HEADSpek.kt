package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object HEADSpek : Spek({
    describe("HEAD") {
        context("extractMethod") {
            it("should return HEAD") {
                val annotation = mock<PsiAnnotation> {}
                Head(annotation).extractMethod() shouldBeEqualTo "HEAD"
            }
        }
    }
})
