package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.utils.fetchAnnotatedElement
import com.viartemev.requestmapper.utils.unquote

abstract class JaxRsMappingAnnotation(val psiAnnotation: PsiAnnotation) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        return fetchRequestMappingItem(psiAnnotation.fetchAnnotatedElement() as PsiMethod, extractMethod())
    }

    abstract fun extractMethod(): String

    internal fun fetchRequestMappingItem(psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMapping = fetchClassMapping(psiMethod)

        return fetchMethodMapping(psiMethod).
                map { RequestMappingItem(psiMethod, classMapping + it, method) }
    }

    private fun fetchClassMapping(psiMethod: PsiMethod): String {
        val annotations = psiMethod.
                containingClass?.
                modifierList?.
                annotations ?: emptyArray()
        val attributes = annotations.
                filter { it != null && it.qualifiedName == PATH_ANNOTATION }.
                map { it.findAttributeValue(ATTRIBUTE_NAME)?.text?.unquote() ?: "" }
        return if (attributes.isNotEmpty()) attributes.first() else ""
    }

    private fun fetchMethodMapping(psiMethod: PsiMethod): List<String> {
        val paths = psiMethod.
                modifierList.
                annotations.
                filter { it.qualifiedName == PATH_ANNOTATION }.
                map { it.findAttributeValue(ATTRIBUTE_NAME)?.text?.unquote() ?: "" }
        return if (paths.isNotEmpty()) paths else listOf("")
    }

    companion object {
        private val PATH_ANNOTATION = "javax.ws.rs.Path"
        private val ATTRIBUTE_NAME = "value"
    }

}