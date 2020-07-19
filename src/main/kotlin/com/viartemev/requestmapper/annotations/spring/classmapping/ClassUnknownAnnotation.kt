package com.viartemev.requestmapper.annotations.spring.classmapping

import com.viartemev.requestmapper.BoundType

class ClassUnknownAnnotation : SpringClassMappingAnnotation {
    override fun fetchClassMapping(): List<String> {
        return listOf()
    }

    override fun fetchBoundMappingFromClass(): Set<BoundType> {
        return emptySet()
    }
}
