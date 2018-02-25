package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.utils.fetchAnnotatedMethod
import com.viartemev.requestmapper.utils.unquote

abstract class JaxRsMappingAnnotation(val psiAnnotation: PsiAnnotation) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        return fetchRequestMappingItem(psiAnnotation.fetchAnnotatedMethod(), extractMethod())
    }

    abstract fun extractMethod(): String

    private fun fetchRequestMappingItem(psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMapping = fetchClassMapping(psiMethod)
        val methodMapping = fetchMethodMapping(psiMethod)
        return listOf(RequestMappingItem(psiMethod, if (classMapping.isBlank() && methodMapping.isBlank()) "/" else classMapping + methodMapping, method))
    }

    private fun fetchClassMapping(psiMethod: PsiMethod): String {
        return psiMethod
            .containingClass
            ?.modifierList
            ?.annotations
            ?.asSequence()
            ?.filter { it.qualifiedName == PATH_ANNOTATION }
            ?.mapNotNull { it.findAttributeValue(ATTRIBUTE_NAME)?.text?.unquote() }
            ?.first() ?: ""
    }

    private fun fetchMethodMapping(psiMethod: PsiMethod): String {
        return psiMethod
            .modifierList
            .annotations
            .filter { it.qualifiedName == PATH_ANNOTATION }
            .mapNotNull { it.findAttributeValue(ATTRIBUTE_NAME)?.text?.unquote() }
            .firstOrNull() ?: ""
    }

    companion object {
        private const val PATH_ANNOTATION = "javax.ws.rs.Path"
        private const val ATTRIBUTE_NAME = "value"
    }
}
