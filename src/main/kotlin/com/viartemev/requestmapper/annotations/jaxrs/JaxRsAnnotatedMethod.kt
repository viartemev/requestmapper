package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiParameter
import com.viartemev.requestmapper.annotations.extraction.PathAnnotation
import com.viartemev.requestmapper.annotations.jaxrs.JaxRsMappingAnnotation.Companion.ATTRIBUTE_NAME
import com.viartemev.requestmapper.annotations.jaxrs.JaxRsMappingAnnotation.Companion.PATH_ANNOTATION
import com.viartemev.requestmapper.model.Path
import com.viartemev.requestmapper.utils.unquote

class JaxRsAnnotatedMethod(private val method: PsiMethod) {

    fun fetchMappingFromMethod(): String {
        val parametersNameWithType = method
            .parameterList
            .parameters
            .mapNotNull { extractParameterNameWithType(it) }
            .toMap()

        return method
            .modifierList
            .annotations
            .asSequence()
            .filter { it.qualifiedName == PATH_ANNOTATION }
            .map { PathAnnotation(it) }
            .toList()
            .flatMap { it.fetchMappings(ATTRIBUTE_NAME) }
            .asSequence()
            .map { Path(it).addPathVariablesTypes(parametersNameWithType) }
            .map { it.toFullPath() }
            .firstOrNull() ?: ""
    }

    private fun extractParameterNameWithType(parameter: PsiParameter): Pair<String, String>? {
        val parameterType = parameter.type.presentableText.unquote()

        return parameter
            .modifierList
            ?.annotations
            ?.filter { it.qualifiedName == JaxRsMappingAnnotation.PATH_PARAM_ANNOTATION }
            ?.map { Pair(JaxRsPathParamAnnotation(it).extractParameterNameOrDefault(parameter.name!!), parameterType) }
            ?.firstOrNull()
    }
}
