package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiLiteralExpression
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PsiBinaryExpressionExtractorSpek : Spek({

    describe("PsiBinaryExpressionExtractor") {
        context("extract with 2 PsiLiteralExpressions") {
            it("should return list with sum of literal expressions texts") {
                val literalExpression = mock<PsiLiteralExpression> {
                    on { text } doReturn "api"
                }
                val psiBinaryExpression = mock<PsiBinaryExpression> {
                    on { lOperand } doReturn literalExpression
                    on { rOperand } doReturn literalExpression
                }
                val extract = PsiBinaryExpressionExtractor().extract(psiBinaryExpression)
                extract.size shouldBeEqualTo 1
                extract shouldContain "apiapi"
            }
        }
    }
})
