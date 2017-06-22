package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiAnnotationMemberValue

interface PsiAnnotationValueExtractor<in T : PsiAnnotationMemberValue> {

    fun extract(value: T): List<String>

}
