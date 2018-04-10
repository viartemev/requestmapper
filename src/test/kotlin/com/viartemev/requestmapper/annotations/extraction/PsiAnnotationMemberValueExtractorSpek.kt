package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PsiAnnotationMemberValueExtractorSpek : Spek({
    describe("PsiAnnotationMemberValueExtractor") {
        on("extract on PsiAnnotationMemberValue with blank text") {
            it("should return empty list") {
                val psiAnnotationMemberValue = mock<PsiAnnotationMemberValue> {
                    on { text } doReturn ""
                }
                PsiAnnotationMemberValueExtractor().extract(psiAnnotationMemberValue).shouldBeEmpty()
            }
        }
        on("extract on PsiAnnotationMemberValue with not blank text") {
            it("should return list with unquoted text") {
                val psiAnnotationMemberValue = mock<PsiAnnotationMemberValue> {
                    on { text } doReturn "\"api\""
                }
                val extract = PsiAnnotationMemberValueExtractor().extract(psiAnnotationMemberValue)
                extract.size shouldEqualTo 1
                extract shouldContain "api"
            }
        }
    }
})
