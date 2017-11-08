package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.*
import com.intellij.psi.impl.java.stubs.index.JavaFieldNameIndex
import com.viartemev.requestmapper.utils.unquote

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> {
        return listOf(extractPath(value))
    }

    private fun extractPath(value: PsiReferenceExpression): String {
        return value.referenceName?.let {
            JavaFieldNameIndex
                    .getInstance()
                    //TODO fix scopes
                    .get(it, value.project, value.resolveScope)
                    .asSequence()
                    .filterIsInstance<PsiField>()
                    .flatMap { it.children.asSequence() }
                    .filter { it is PsiBinaryExpression || it is PsiLiteralExpression || it is PsiPolyadicExpression }
                    .map { doMagin(it) }
                    .toList()[0]
        } ?: ""
        //TODO replace empty string
    }

    private fun doMagin(psiElement: PsiElement): String {
        if (psiElement is PsiLiteralExpression) {
            return psiElement.text.unquote()
        }
        if (psiElement is PsiBinaryExpression) {
            return if (psiElement.lOperand is PsiLiteralExpression && psiElement.rOperand is PsiReferenceExpression) {
                doMagin(psiElement.lOperand as PsiLiteralExpression) + extractPath(psiElement.rOperand as PsiReferenceExpression)
            } else if (psiElement.lOperand is PsiReferenceExpression && psiElement.rOperand is PsiLiteralExpression) {
                extractPath(psiElement.lOperand as PsiReferenceExpression) + doMagin(psiElement.rOperand as PsiLiteralExpression)
            } else {
                doMagin(psiElement.lOperand as PsiLiteralExpression) + doMagin(psiElement.rOperand as PsiLiteralExpression)
            }
        }
        if (psiElement is PsiPolyadicExpression) {
            var result = ""
            for (operand in psiElement.operands) {
                if (operand is PsiLiteralExpression) {
                    result += doMagin(operand)
                } else if (operand is PsiReferenceExpression) {
                    result += extractPath(operand)
                }
            }
            return result
        }
        //TODO replace empty string
        return ""
    }

}


