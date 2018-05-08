package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReferenceExpression
import com.viartemev.requestmapper.annotations.extraction.PsiExpressionExtractor

class JaxRsPathParamAnnotation(private val annotation: PsiAnnotation) {

    fun extractParameterNameOrDefault(defaultValue: String): String {
        val pathVariableValue = annotation.findAttributeValue(JaxRsMappingAnnotation.ATTRIBUTE_NAME)
        return when (pathVariableValue) {
            is PsiLiteralExpression -> {
                val expression = PsiExpressionExtractor.extractExpression(pathVariableValue)
                if (expression.isNotBlank()) expression else defaultValue
            }
            is PsiReferenceExpression -> {
                val expression = PsiExpressionExtractor.extractExpression(pathVariableValue)
                if (expression.isNotBlank()) expression else defaultValue
            }
            else -> defaultValue
        }
    }
}
