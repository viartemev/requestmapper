package com.viartemev.requestmapper.model

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiParameter
import com.viartemev.requestmapper.utils.unquote

class PathParameter(private val parameter: PsiParameter) {

    fun extractParameterNameWithType(annotationName: String, extractParameterNameFunction: (annotation: PsiAnnotation, defaultValue: String) -> String): Pair<String, String>? =
        parameter
            .modifierList
            ?.annotations
            ?.filter { it.qualifiedName == annotationName }
            ?.map { Pair(extractParameterNameFunction(it, parameter.name!!), parameter.type.presentableText.unquote()) }
            ?.firstOrNull()
}
