package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PostMappingSpek : Spek({
    describe("PostMapping") {
        on("extractMethod") {
            it("should return POST") {
                val annotation = mock<PsiAnnotation> {}
                PostMapping(annotation).extractMethod() shouldBeEqualTo "POST"
            }
        }
    }
})
