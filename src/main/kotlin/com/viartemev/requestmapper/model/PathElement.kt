package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.addCurlyBrackets
import com.viartemev.requestmapper.utils.inCurlyBrackets
import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

class PathElement(val value: String) {
    val isPathVariable: Boolean = value.inCurlyBrackets()

    fun addPathVariableType(type: String) =
            if (isPathVariable) PathElement(value.unquoteCurlyBrackets().let { "${if (type.isBlank()) "String" else type}:$it" }.addCurlyBrackets())
            else this
}