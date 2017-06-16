package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiReferenceExpression
import org.apache.commons.lang.StringUtils

abstract class BasePsiAnnotationValueVisitor : PsiAnnotationValueVisitor {

    fun visit(annotation: PsiAnnotation, parameter: String): List<String> {
        val valueParam = annotation.findAttributeValue(parameter)
        return if (valueParam is PsiArrayInitializerMemberValue) {
            visitPsiArrayInitializerMemberValue(valueParam)
        } else if (valueParam is PsiReferenceExpression) {
            val visitPsiReferenceExpression = visitPsiReferenceExpression((valueParam))
            visitPsiReferenceExpression
        } else if (valueParam != null && valueParam.text.isNotEmpty()) {
            visitPsiAnnotationMemberValue(valueParam)
        } else {
            emptyList()
        }
    }

}
