package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.addCurlyBrackets
import com.viartemev.requestmapper.utils.inCurlyBrackets
import com.viartemev.requestmapper.utils.isNumeric
import com.viartemev.requestmapper.utils.unquoteCurlyBrackets
import org.apache.commons.lang.StringEscapeUtils

class PathElement(val value: String) {
    val isPathVariable: Boolean = value.inCurlyBrackets()

    fun addPathVariableType(type: String) = if (isPathVariable) PathElement(value.unquoteCurlyBrackets().let { "${if (type.isBlank()) "String" else type}:$it" }.addCurlyBrackets())
    else this

    private fun compareWithPathVariable(pathElement: PathElement, searchPattern: PathElement): Boolean =
        if ((pathElement.isPathVariable && searchPattern.isPathVariable) || searchPattern.isPathVariable) {
            comparePathVariables(searchPattern, pathElement)
        } else {
            compareSearchElementWithPathElement(searchPattern, pathElement)
        }

    private fun comparePathVariables(searchPattern: PathElement, pathElement: PathElement): Boolean {
        val searchPatternValue = StringEscapeUtils.unescapeJava(searchPattern.value.unquoteCurlyBrackets().substringAfterLast(':'))
        val pathElementValue = StringEscapeUtils.unescapeJava(pathElement.value.unquoteCurlyBrackets().substringAfterLast(':'))
        return pathElementValue.contains(searchPatternValue)
    }

    private fun compareSearchElementWithPathElement(searchPattern: PathElement, pathElement: PathElement): Boolean {
        if (searchPattern.value.count { it == ':' } > 1) {
            val regexString = StringEscapeUtils.unescapeJava(searchPattern.value.unquoteCurlyBrackets().substringAfterLast(':'))
            return regexString.toRegex().matches(pathElement.value)
        }
        val bothAreNumbers = isDigit(pathElement.value) && searchPattern.value.isNumeric()
        val pathVariableIsNotNumber = !isDigit(pathElement.value)
        return bothAreNumbers || pathVariableIsNotNumber
    }

    /**
     * Format of curly brackets values:
     * String:items
     * Int:itemId
     * Long:itemId
     */
    private fun isDigit(originalElement: String) = when (originalElement.unquoteCurlyBrackets().split(":").first()) {
        "int" -> true
        "long" -> true
        "float" -> true
        "double" -> true

        "Integer" -> true
        "Long" -> true
        "Float" -> true
        "Double" -> true

        "BigInteger" -> true
        "BigDecimal" -> true

        else -> false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PathElement

        if (!this.isPathVariable && !other.isPathVariable) return value == other.value

        return compareWithPathVariable(this, other)
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "PathElement(value='$value', isPathVariable=$isPathVariable)"
    }
}
