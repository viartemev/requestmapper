package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object DeleteMappingSpek : Spek({
    describe("DeleteMapping") {
        on("extractMethod") {
            it("should return DELETE") {
                val annotation = mock<PsiAnnotation> {}
                DeleteMapping(annotation).extractMethod() shouldBeEqualTo "DELETE"
            }
        }
    }
})
