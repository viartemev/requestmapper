package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PsiArrayInitializerMemberValueExtractorSpek : Spek({
    describe("PsiAnnotationMemberValueExtractor") {
        context("extract with empty initializers array") {
            it("should return empty list") {
                val psiArrayInitializerMemberValue = mock<PsiArrayInitializerMemberValue> {
                    on { initializers } doReturn emptyArray<PsiAnnotationMemberValue>()
                }
                PsiArrayInitializerMemberValueExtractor().extract(psiArrayInitializerMemberValue).shouldBeEmpty()
            }
        }
        context("extract with not empty initializers array") {
            it("should return list with unquoted initializers texts") {
                val psiAnnotationMemberValue = mock<PsiAnnotationMemberValue> {
                    on { text } doReturn "\"api\""
                }
                val psiArrayInitializerMemberValue = mock<PsiArrayInitializerMemberValue> {
                    on { initializers } doReturn arrayOf(psiAnnotationMemberValue, psiAnnotationMemberValue)
                }
                val extract = PsiArrayInitializerMemberValueExtractor().extract(psiArrayInitializerMemberValue)
                extract.size shouldEqualTo 2
                extract shouldContainAll listOf("api", "api")
            }
        }
    }
})
