package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReferenceExpression
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PsiReferenceExpressionExtractorSpek : Spek({
    describe("PsiReferenceExpressionExtractor") {
        context("extract with PsiLiteralExpression") {
            it("should return list with sum of literal expressions texts") {
                val psiElement = mock<PsiLiteralExpression> {
                    on { text } doReturn "\"api\""
                }
                val psiReferenceExpression = mock<PsiReferenceExpression> {
                    on { resolve() } doReturn it
                    on { children } doReturn arrayOf(psiElement)
                }
                val extract = PsiReferenceExpressionExtractor().extract(psiReferenceExpression)
                extract.size shouldBeEqualTo 1
                extract shouldContain "api"
            }
        }
    }
})
