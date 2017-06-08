package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.viartemev.requestmapper.utils.unquote

class PsiAnnotationMemberValueExtractor : PsiAnnotationValueExtractor<PsiAnnotationMemberValue> {
    override fun extract(value: PsiAnnotationMemberValue): List<String> {
        return if (value.text.isNotBlank()) listOf(value.text.unquote()) else emptyList<String>()
    }
}
