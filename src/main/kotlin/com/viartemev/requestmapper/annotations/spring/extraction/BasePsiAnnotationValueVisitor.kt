package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiReferenceExpression
import org.apache.commons.lang.StringUtils

abstract class BasePsiAnnotationValueVisitor : PsiAnnotationValueVisitor {
    fun visit(annotation: PsiAnnotation, parameter: String): List<String> {
        var result = emptyList<String>()
        val valueParam = annotation.findAttributeValue(parameter)
        if (valueParam is PsiArrayInitializerMemberValue) {
            result = visitPsiArrayInitializerMemberValue((valueParam as PsiArrayInitializerMemberValue?)!!)
        } else if (valueParam is PsiReferenceExpression) {
            result = visitPsiReferenceExpression((valueParam as PsiReferenceExpression?)!!)
        } else if (valueParam != null && StringUtils.isNotEmpty(valueParam.text)) {
            result = visitPsiAnnotationMemberValue(valueParam)
        }
        return result
    }
}
