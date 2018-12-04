package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object POSTSpek : Spek({
    describe("POST") {
        context("extractMethod") {
            it("should return POST") {
                val annotation = mock<PsiAnnotation> {}
                POST(annotation).extractMethod() shouldBeEqualTo "POST"
            }
        }
    }
})
