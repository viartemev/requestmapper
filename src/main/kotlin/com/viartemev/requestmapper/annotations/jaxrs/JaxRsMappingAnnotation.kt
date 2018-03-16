package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiParameter
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.annotations.PathAnnotation
import com.viartemev.requestmapper.model.Path
import com.viartemev.requestmapper.utils.fetchAnnotatedMethod
import com.viartemev.requestmapper.utils.unquote

abstract class JaxRsMappingAnnotation(val psiAnnotation: PsiAnnotation) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        return fetchRequestMappingItem(psiAnnotation.fetchAnnotatedMethod(), extractMethod())
    }

    abstract fun extractMethod(): String

    private fun fetchRequestMappingItem(psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMapping = fetchMappingFromClass(psiMethod)
        val methodMapping = fetchMappingFromMethod(psiMethod)
        return listOf(RequestMappingItem(psiMethod, if (classMapping.isBlank() && methodMapping.isBlank()) "/" else classMapping + methodMapping, method))
    }

    private fun fetchMappingFromClass(psiMethod: PsiMethod): String {
        return psiMethod
                .containingClass
                ?.modifierList
                ?.annotations
                ?.filter { it.qualifiedName == PATH_ANNOTATION }
                ?.flatMap { PathAnnotation(it).fetchMappings(ATTRIBUTE_NAME) }
                ?.first() ?: ""
    }

    private fun fetchMappingFromMethod(method: PsiMethod): String {
        val parametersNameWithType = method
                .parameterList
                .parameters
                .mapNotNull { extractParameterNameWithType(it) }
                .toMap()

        return method
                .modifierList
                .annotations
                .filter { it.qualifiedName == PATH_ANNOTATION }
                .flatMap { PathAnnotation(it).fetchMappings(ATTRIBUTE_NAME) }
                .map { Path(it).addPathVariablesTypes(parametersNameWithType).toFullPath() }
                .firstOrNull() ?: ""
    }

    private fun extractParameterNameWithType(parameter: PsiParameter): Pair<String, String>? {
        val parameterType = parameter.type.presentableText.unquote()
        return parameter
                .modifierList
                ?.annotations
                ?.filter { it.qualifiedName == JAX_RS_PATH_PARAM }
                ?.map { Pair(extractParameterNameFromAnnotation(it, parameter.name!!), parameterType) }
                ?.first()
    }

    private fun extractParameterNameFromAnnotation(annotation: PsiAnnotation, defaultValue: String): String {
        val pathVariableValue = annotation.findAttributeValue(ATTRIBUTE_NAME)
        return when {
            pathVariableValue?.text?.unquote()?.isNotBlank() == true -> pathVariableValue.text.unquote()
            else -> defaultValue
        }
    }

    companion object {
        private const val PATH_ANNOTATION = "javax.ws.rs.Path"
        private const val ATTRIBUTE_NAME = "value"
        private const val JAX_RS_PATH_PARAM = "javax.ws.rs.PathParam"
    }
}
