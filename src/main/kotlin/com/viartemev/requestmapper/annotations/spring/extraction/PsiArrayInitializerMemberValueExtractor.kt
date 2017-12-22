package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiArrayInitializerMemberValue
import com.viartemev.requestmapper.utils.unquote

class PsiArrayInitializerMemberValueExtractor : PsiAnnotationValueExtractor<PsiArrayInitializerMemberValue> {

    override fun extract(value: PsiArrayInitializerMemberValue): List<String> = value.initializers.map { it.text.unquote() }
}
