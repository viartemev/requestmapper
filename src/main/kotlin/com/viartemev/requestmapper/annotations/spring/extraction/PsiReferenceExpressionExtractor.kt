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
            val element = JavaFieldNameIndex
                    .getInstance()
                    //TODO fix scopes
                    .get(it, value.project, value.resolveScope)
                    .asSequence()
                    .filterIsInstance<PsiField>()
                    .flatMap { it.children.asSequence() }
                    .filter { it is PsiBinaryExpression || it is PsiLiteralExpression || it is PsiPolyadicExpression }
                    .toList()[0]
            extractPath(element)
        } ?: ""
        //TODO replace empty string
    }

    private fun extractPath(psiElement: PsiElement): String {
        if (psiElement is PsiLiteralExpression) {
            return psiElement.text.unquote()
        }
        if (psiElement is PsiBinaryExpression) {
            return if (psiElement.lOperand is PsiLiteralExpression && psiElement.rOperand is PsiReferenceExpression) {
                (psiElement.lOperand as PsiLiteralExpression).text.unquote() + extractPath(psiElement.rOperand as PsiReferenceExpression)
            } else {
                extractPath(psiElement.lOperand as PsiReferenceExpression) + (psiElement.rOperand as PsiLiteralExpression).text.unquote()
            }
        }
        if (psiElement is PsiPolyadicExpression) {
            //TODO implement this case
            return "PsiPolyadicExpression"
        }
        //TODO replace empty string
        return ""
    }

}


