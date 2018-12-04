package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object DeleteMappingSpek : Spek({
    describe("DeleteMapping") {
        context("extractMethod") {
            it("should return DELETE") {
                val annotation = mock<PsiAnnotation> {}
                DeleteMapping(annotation).extractMethod() shouldBeEqualTo "DELETE"
            }
        }
    }
})
