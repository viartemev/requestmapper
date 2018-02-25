package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiReferenceExpression

abstract class BasePsiAnnotationValueVisitor : PsiAnnotationValueVisitor {

    fun visit(annotation: PsiAnnotation, parameter: String): List<String> {
        val attributeValue = annotation.findAttributeValue(parameter)
        return when (attributeValue) {
            is PsiArrayInitializerMemberValue -> visitPsiArrayInitializerMemberValue(attributeValue)
            is PsiReferenceExpression -> visitPsiReferenceExpression(attributeValue)
            else -> if (attributeValue != null && attributeValue.text.isNotBlank()) visitPsiAnnotationMemberValue(attributeValue) else emptyList()
        }
    }
}
