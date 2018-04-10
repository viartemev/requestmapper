package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PUTSpek : Spek({
    describe("PUT") {
        on("extractMethod") {
            it("should return PUT") {
                val annotation = mock<PsiAnnotation> {}
                PUT(annotation).extractMethod() shouldBeEqualTo "PUT"
            }
        }
    }
})
