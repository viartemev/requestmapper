package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.viartemev.requestmapper.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.viartemev.requestmapper.utils.unquote

class PsiAnnotationMemberValueExtractor : PsiAnnotationValueExtractor<PsiAnnotationMemberValue> {

    override fun extract(value: PsiAnnotationMemberValue): List<String> {
        val element = extractExpression(value)
        return when {
            element.isNotBlank() -> listOf(element)
            value.text.isNotBlank() -> listOf(value.text.unquote())
            else -> emptyList()
        }
    }
}
