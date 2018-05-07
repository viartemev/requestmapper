package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiClass
import com.viartemev.requestmapper.annotations.extraction.PathAnnotation
import com.viartemev.requestmapper.annotations.jaxrs.JaxRsMappingAnnotation.Companion.ATTRIBUTE_NAME
import com.viartemev.requestmapper.annotations.jaxrs.JaxRsMappingAnnotation.Companion.PATH_ANNOTATION

class JaxRsAnnotatedClass(private val psiClass: PsiClass?) {

    fun fetchMappingFromClass() = psiClass
        ?.modifierList
        ?.annotations
        ?.filter { it.qualifiedName == PATH_ANNOTATION }
        ?.flatMap { PathAnnotation(it).fetchMappings(ATTRIBUTE_NAME) }
        ?.firstOrNull() ?: ""
}
