package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiArrayInitializerMemberValue
import com.viartemev.requestmapper.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.viartemev.requestmapper.utils.unquote

class PsiArrayInitializerMemberValueExtractor : PsiAnnotationValueExtractor<PsiArrayInitializerMemberValue> {

    override fun extract(value: PsiArrayInitializerMemberValue): List<String> = value.initializers.map {
        val element = extractExpression(it)
        when {
            element.isNotBlank() -> element
            it.text.isNotBlank() -> it.text.unquote()
            else -> ""
        }
    }
}
