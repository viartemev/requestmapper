package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiReferenceExpression

abstract class BasePsiAnnotationValueVisitor : PsiAnnotationValueVisitor {

    fun visit(annotation: PsiAnnotation, parameter: String): List<String> {
        return when (val attributeValue = annotation.findAttributeValue(parameter)) {
            is PsiArrayInitializerMemberValue -> visitPsiArrayInitializerMemberValue(attributeValue)
            is PsiReferenceExpression -> visitPsiReferenceExpression(attributeValue)
            is PsiBinaryExpression -> visitPsiBinaryExpression(attributeValue)
            is PsiPolyadicExpression -> visitPsiPolyadicExpression(attributeValue)
            else -> if (attributeValue != null && attributeValue.text.isNotBlank()) visitPsiAnnotationMemberValue(attributeValue) else emptyList()
        }
    }
}
