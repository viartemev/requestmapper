package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.addCurlyBrackets
import com.viartemev.requestmapper.utils.inCurlyBrackets
import com.viartemev.requestmapper.utils.isNumeric
import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

class PathElement(val value: String) {
    val isPathVariable: Boolean = value.inCurlyBrackets()

    fun addPathVariableType(type: String) =
            if (isPathVariable) PathElement(value.unquoteCurlyBrackets().let { "${if (type.isBlank()) "String" else type}:$it" }.addCurlyBrackets())
            else this

    private fun haveSimilarType(value1: PathElement, value2: PathElement): Boolean {
        if (value1.isPathVariable) {
            val bothAreNumbers = isDigit(value1.value) && value2.value.isNumeric()
            val pathVariableIsString = !isDigit(value1.value)
            return bothAreNumbers || pathVariableIsString
        } else {
            val bothAreNumbers = isDigit(value2.value) && value1.value.isNumeric()
            val pathVariableIsString = !isDigit(value2.value)
            return bothAreNumbers || pathVariableIsString
        }
    }

    /**
     * Format of curly brackets values:
     * String:items
     * Int:itemId
     * Long:itemId
     */
    private fun isDigit(originalElement: String) = originalElement
            .unquoteCurlyBrackets()
            .split(":")
            .first() != "String"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PathElement

        if (!this.isPathVariable && !other.isPathVariable) return value == other.value

        return haveSimilarType(this, other)
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "PathElement(value='$value', isPathVariable=$isPathVariable)"
    }
}