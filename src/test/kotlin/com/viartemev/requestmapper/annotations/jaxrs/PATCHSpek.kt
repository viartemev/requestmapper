package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PATCHSpek : Spek({
    describe("PATCH") {
        on("extractMethod") {
            it("should return PATCH") {
                val annotation = mock<PsiAnnotation> {}
                PATCH(annotation).extractMethod() shouldBeEqualTo "PATCH"
            }
        }
    }
})
