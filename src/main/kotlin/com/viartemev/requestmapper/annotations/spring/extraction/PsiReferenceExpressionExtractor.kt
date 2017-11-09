package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.*
import com.viartemev.requestmapper.utils.unquote

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> {
        return listOf(extractPath(value))
    }

    private fun extractPath(value: PsiReferenceExpression): String {
        return value.resolve()?.let {
            it
                    .children
                    .asSequence()
                    .filter { it is PsiBinaryExpression || it is PsiLiteralExpression || it is PsiPolyadicExpression }
                    .map { extractExpression(it) }
                    .toList()
                    //only one exists
                    .first()
        } ?: ""
    }

    private fun extractExpression(psiElement: PsiElement): String {
        return when (psiElement) {
            is PsiLiteralExpression -> extractLiteralExpression(psiElement)
            is PsiBinaryExpression -> extractBinaryExpression(psiElement)
            is PsiPolyadicExpression -> extractPsiPolyadicExpression(psiElement)
            is PsiReferenceExpression -> extractPath(psiElement)
            else -> ""
        }
    }

    private fun extractLiteralExpression(psiElement: PsiLiteralExpression): String {
        return psiElement.text.unquote()
    }

    private fun extractBinaryExpression(psiElement: PsiBinaryExpression): String {
        //rOperand always present in static final variables
        return extractExpression(psiElement.lOperand) + extractExpression(psiElement.rOperand!!)
    }

    private fun extractPsiPolyadicExpression(psiElement: PsiPolyadicExpression): String {
        return psiElement
                .operands
                .asSequence()
                .joinToString(
                        separator = "",
                        transform = {
                            when (it) {
                                is PsiLiteralExpression -> extractLiteralExpression(it)
                                is PsiReferenceExpression -> extractPath(it)
                                else -> ""
                            }
                        })
    }

}


