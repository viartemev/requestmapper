package com.viartemev.requestmapper.model

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiParameter
import com.viartemev.requestmapper.utils.unquote

class PathParameter(private val parameter: PsiParameter) {

    fun extractParameterNameWithType(annotationName: String, extractParameterNameFunction: (annotation: PsiAnnotation, defaultValue: String) -> String): Pair<String, String>? {
        val parameterType = parameter.type.presentableText.unquote()

        return parameter
            .modifierList
            ?.annotations
            ?.filter { it.qualifiedName == annotationName }
            ?.map { Pair(extractParameterNameFunction(it, parameter.name), parameterType) }
            ?.firstOrNull()
    }
}
