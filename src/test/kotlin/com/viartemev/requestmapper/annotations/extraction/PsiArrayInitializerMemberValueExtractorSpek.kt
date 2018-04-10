package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PsiArrayInitializerMemberValueExtractorSpek : Spek({
    describe("PsiAnnotationMemberValueExtractor") {
        on("extract with empty initializers array") {
            it("should return empty list") {
                val psiArrayInitializerMemberValue = mock<PsiArrayInitializerMemberValue> {
                    on { initializers } doReturn emptyArray<PsiAnnotationMemberValue>()
                }
                PsiArrayInitializerMemberValueExtractor().extract(psiArrayInitializerMemberValue).shouldBeEmpty()
            }
        }
        on("extract with not empty initializers array") {
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
