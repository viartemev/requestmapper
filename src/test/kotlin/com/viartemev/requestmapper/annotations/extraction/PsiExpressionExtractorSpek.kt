package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAssignmentExpression
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiReferenceExpression
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PsiExpressionExtractorSpek : Spek({
    describe("PsiExpressionExtractor") {
        on("extractExpression on PsiLiteralExpression") {
            it("should return unquoted text") {
                val psiLiteralExpression = mock<PsiLiteralExpression> {
                    on { text } doReturn "\"api\""
                }
                PsiExpressionExtractor.extractExpression(psiLiteralExpression) shouldBeEqualTo "api"
            }
        }
        on("extractExpression on PsiReferenceExpression") {
            it("should return unquoted text") {
                val psiElement = mock<PsiLiteralExpression> {
                    on { text } doReturn "\"api\""
                }
                val psiReferenceExpression = mock<PsiReferenceExpression> {
                    on { resolve() } doReturn it
                    on { children } doReturn arrayOf(psiElement)
                }
                PsiExpressionExtractor.extractExpression(psiReferenceExpression) shouldBeEqualTo "api"
            }
        }
        on("extractExpression on PsiReferenceExpression on not resolved value") {
            it("should return empty list") {
                val psiReferenceExpression = mock<PsiReferenceExpression> {
                    on { children } doReturn emptyArray<PsiExpression>()
                }
                PsiExpressionExtractor.extractExpression(psiReferenceExpression) shouldBeEqualTo ""
            }
        }
        on("extractExpression on PsiBinaryExpression") {
            it("should return sum of the left operator and the right operator") {
                val psiElement = mock<PsiLiteralExpression> {
                    on { text } doReturn "\"api\""
                }
                val psiBinaryExpression = mock<PsiBinaryExpression> {
                    on { lOperand } doReturn psiElement
                    on { rOperand } doReturn psiElement
                }
                PsiExpressionExtractor.extractExpression(psiBinaryExpression) shouldBeEqualTo "apiapi"
            }
        }
        on("extractExpression on PsiPolyadicExpression") {
            it("should return joined string of an each expression") {
                val psiElement = mock<PsiLiteralExpression> {
                    on { text } doReturn "\"api\""
                }
                val psiPolyadicExpression = mock<PsiPolyadicExpression> {
                    on { operands } doReturn arrayOf<PsiExpression>(psiElement, psiElement)
                }
                PsiExpressionExtractor.extractExpression(psiPolyadicExpression) shouldBeEqualTo "apiapi"
            }
        }
        on("extractExpression on others expressions") {
            it("should return empty string") {
                val psiAssignmentExpression = mock<PsiAssignmentExpression> {}
                PsiExpressionExtractor.extractExpression(psiAssignmentExpression) shouldBeEqualTo ""
            }
        }
    }
})
