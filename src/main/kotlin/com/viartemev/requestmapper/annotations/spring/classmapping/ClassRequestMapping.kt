package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.annotations.spring.fetchPathValueMapping

class ClassRequestMapping(val psiAnnotation: PsiAnnotation) : SpringClassMappingAnnotation {
    override fun fetchClassMapping(): List<String> {
        return fetchPathValueMapping(psiAnnotation)
    }

    override fun fetchBoundMappingFromClass(): Set<BoundType> {
        return setOf(BoundType.INBOUND)
    }
}
