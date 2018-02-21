package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.addCurlyBrackets
import com.viartemev.requestmapper.utils.inCurlyBrackets
import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class PathElement(val value: String) {

    fun addPathVariableType(type: String) =
            if (hasPathVariable()) this.copy(value = value.unquoteCurlyBrackets().let { "$type:$it" }.addCurlyBrackets())
            else this

    private fun hasPathVariable() = value.inCurlyBrackets()
}