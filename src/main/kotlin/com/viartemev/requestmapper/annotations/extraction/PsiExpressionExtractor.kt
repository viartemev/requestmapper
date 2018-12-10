package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiReferenceExpression
import com.viartemev.requestmapper.utils.unquote

object PsiExpressionExtractor {

    fun extractExpression(psiElement: PsiElement): String = when (psiElement) {
        is PsiLiteralExpression -> extractLiteralExpression(psiElement)
        is PsiBinaryExpression -> extractBinaryExpression(psiElement)
        is PsiPolyadicExpression -> extractPsiPolyadicExpression(psiElement)
        is PsiReferenceExpression -> extractPath(psiElement)
        else -> ""
    }

    private fun extractPath(value: PsiReferenceExpression): String =
        value
            .resolve()
            ?.children
            ?.asSequence()
            ?.filter { it is PsiBinaryExpression || it is PsiLiteralExpression || it is PsiPolyadicExpression }
            ?.map { extractExpression(it) }
            ?.toList()
            ?.first()
            ?: ""

    private fun extractLiteralExpression(psiElement: PsiLiteralExpression) = psiElement.text.unquote()

    /** rOperand always presents in static final variables */
    private fun extractBinaryExpression(psiElement: PsiBinaryExpression) =
        extractExpression(psiElement.lOperand) + extractExpression(psiElement.rOperand!!)

    private fun extractPsiPolyadicExpression(psiElement: PsiPolyadicExpression) =
        psiElement.operands.joinToString(separator = "", transform = { extractExpression(it) })
}
