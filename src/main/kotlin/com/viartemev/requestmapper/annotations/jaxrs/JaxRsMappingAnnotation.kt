package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReferenceExpression
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.annotations.UrlFormatter
import com.viartemev.requestmapper.annotations.extraction.PathAnnotation
import com.viartemev.requestmapper.annotations.extraction.PsiExpressionExtractor.extractExpression
import com.viartemev.requestmapper.model.Path
import com.viartemev.requestmapper.model.PathParameter
import com.viartemev.requestmapper.utils.fetchAnnotatedMethod

abstract class JaxRsMappingAnnotation(
    private val psiAnnotation: PsiAnnotation,
    private val urlFormatter: UrlFormatter = JaxRsUrlFormatter
) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        val psiMethod = psiAnnotation.fetchAnnotatedMethod()
        val classMapping = fetchMappingFromClass(psiMethod)
        val methodMapping = fetchMappingFromMethod(psiMethod)
        return listOf(RequestMappingItem(psiMethod, urlFormatter.format(classMapping, methodMapping), extractMethod()))
    }

    abstract fun extractMethod(): String

    private fun fetchMappingFromClass(psiMethod: PsiMethod) = psiMethod
                .containingClass
                ?.modifierList
                ?.annotations
                ?.filter { it.qualifiedName == PATH_ANNOTATION }
                ?.flatMap { PathAnnotation(it).fetchMappings(ATTRIBUTE_NAME) }
                ?.firstOrNull() ?: ""

    private fun fetchMappingFromMethod(method: PsiMethod): String {
        val parametersNameWithType = method
                .parameterList
                .parameters
                .mapNotNull { PathParameter(it).extractParameterNameWithType(PATH_PARAM_ANNOTATION, ::extractParameterNameFromAnnotation) }
                .toMap()

        return method
                .modifierList
                .annotations
                .filter { it.qualifiedName == PATH_ANNOTATION }
                .flatMap { PathAnnotation(it).fetchMappings(ATTRIBUTE_NAME) }
                .map { Path(it).addPathVariablesTypes(parametersNameWithType).toFullPath() }
                .firstOrNull() ?: ""
    }

    private fun extractParameterNameFromAnnotation(annotation: PsiAnnotation, defaultValue: String): String {
        val pathVariableValue = annotation.findAttributeValue(ATTRIBUTE_NAME)
        return when (pathVariableValue) {
            is PsiLiteralExpression -> {
                val expression = extractExpression(pathVariableValue)
                if (expression.isNotBlank()) expression else defaultValue
            }
            is PsiReferenceExpression -> {
                val expression = extractExpression(pathVariableValue)
                if (expression.isNotBlank()) expression else defaultValue
            }
            else -> defaultValue
        }
    }

    companion object {
        private const val PATH_ANNOTATION = "javax.ws.rs.Path"
        private const val ATTRIBUTE_NAME = "value"
        private const val PATH_PARAM_ANNOTATION = "javax.ws.rs.PathParam"
    }
}
