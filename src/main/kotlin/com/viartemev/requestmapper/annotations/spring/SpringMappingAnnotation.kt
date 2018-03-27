package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.model.Path
import com.viartemev.requestmapper.annotations.PathAnnotation
import com.viartemev.requestmapper.model.PathParameter
import com.viartemev.requestmapper.utils.fetchAnnotatedMethod
import com.viartemev.requestmapper.utils.unquote

abstract class SpringMappingAnnotation(val psiAnnotation: PsiAnnotation) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> =
            fetchRequestMappingItem(psiAnnotation, psiAnnotation.fetchAnnotatedMethod(), extractMethod())

    abstract fun extractMethod(): String

    private fun fetchRequestMappingItem(annotation: PsiAnnotation, psiMethod: PsiMethod, methodName: String): List<RequestMappingItem> {
        val classMappings = fetchMappingsFromClass(psiMethod)
        val methodMappings = fetchMappingsFromMethod(annotation, psiMethod)
        val paramsMappings = fetchMappingsParams(annotation)
        return classMappings.map { clazz ->
            methodMappings.map {
                paramsMappings.map { param ->
                    val urlPath = if (clazz.isBlank() && it.isBlank()) "/" else "$clazz$it${if (param.isNotBlank()) " params=$param" else ""}"
                    RequestMappingItem(psiMethod, urlPath, methodName)
                }
            }.flatten()
        }.flatten()
    }

    private fun fetchMappingsParams(annotation: PsiAnnotation): List<String> {
        val fetchMappingsFromAnnotation = PathAnnotation(annotation).fetchMappings(PARAMS)
        return if (fetchMappingsFromAnnotation.isNotEmpty()) fetchMappingsFromAnnotation else listOf("")
    }

    private fun fetchMappingsFromClass(psiMethod: PsiMethod): List<String> {
        val classMapping = psiMethod
                .containingClass
                ?.modifierList
                ?.annotations
                ?.filterNotNull()
                ?.filter { it.qualifiedName == SPRING_REQUEST_MAPPING_CLASS }
                ?.flatMap { fetchMapping(it) } ?: emptyList()
        return if (classMapping.isEmpty()) listOf("") else classMapping
    }

    private fun fetchMapping(annotation: PsiAnnotation): List<String> {
        val pathMapping = PathAnnotation(annotation).fetchMappings(PATH)
        return if (!pathMapping.isEmpty()) pathMapping else {
            val valueMapping = PathAnnotation(annotation).fetchMappings(VALUE)
            if (valueMapping.isNotEmpty()) valueMapping else listOf("")
        }
    }

    private fun fetchMappingsFromMethod(annotation: PsiAnnotation, method: PsiMethod): List<String> {
        val parametersNameWithType = method
                .parameterList
                .parameters
                .mapNotNull { PathParameter(it).extractParameterNameWithType(SPRING_PATH_VARIABLE_CLASS, ::extractParameterNameFromAnnotation) }
                .toMap()

        return fetchMapping(annotation)
                .map { Path(it).addPathVariablesTypes(parametersNameWithType).toFullPath() }
    }

    private fun extractParameterNameFromAnnotation(annotation: PsiAnnotation, defaultValue: String): String {
        val pathVariableValue = annotation.findAttributeValue(VALUE)
        val pathVariableName = annotation.findAttributeValue(NAME)
        return when {
            pathVariableValue?.text?.unquote()?.isNotBlank() == true -> pathVariableValue.text.unquote()
            pathVariableName?.text?.unquote()?.isNotBlank() == true -> pathVariableName.text.unquote()
            else -> defaultValue
        }
    }

    companion object {
        private const val VALUE = "value"
        private const val PATH = "path"
        private const val NAME = "name"
        private const val PARAMS = "params"
        private const val SPRING_REQUEST_MAPPING_CLASS = "org.springframework.web.bind.annotation.RequestMapping"
        private const val SPRING_PATH_VARIABLE_CLASS = "org.springframework.web.bind.annotation.PathVariable"
    }
}
