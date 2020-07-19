package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.annotations.PathAnnotation

fun fetchPathValueMapping(annotation: PsiAnnotation): List<String> {
    val pathMapping = PathAnnotation(annotation).fetchMappings(PATH)
    return if (pathMapping.isNotEmpty()) pathMapping else {
        val valueMapping = PathAnnotation(annotation).fetchMappings(VALUE)
        if (valueMapping.isNotEmpty()) valueMapping else listOf("")
    }
}

private const val VALUE = "value"
private const val PATH = "path"
