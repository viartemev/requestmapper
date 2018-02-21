package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.inCurlyBrackets
import com.viartemev.requestmapper.utils.isNumeric
import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>) =
            this.copy(pathElements = pathElements.map { it.addPathVariableType(parametersNameWithType.getOrDefault(it.value.unquoteCurlyBrackets(), "String")) })

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    // @todo #57 rewrite isSimilarTo method
    fun isSimilarTo(anotherPath: Path): Boolean {
        return isSimilarPaths(this, anotherPath)
    }

    private tailrec fun isSimilarPaths(popupItemPath: Path,
                                       userPatternPath: Path,
                                       matches: Boolean = false): Boolean {
        if (matches) {
            return true
        }
        if (popupItemPath.pathElements.size < userPatternPath.pathElements.size) {
            return false
        }
        val listMatches = matches(popupItemPath.pathElements, userPatternPath.pathElements)

        return isSimilarPaths(Path(popupItemPath.pathElements.drop(1)), userPatternPath, listMatches)
    }

    private fun matches(popupItemList: List<PathElement>,
                        userPatternList: List<PathElement>): Boolean {
        val size = userPatternList.size
        popupItemList.forEachIndexed { index, pathElement ->
            if (index == size) {
                return@matches false
            }
            val userPatternElement = userPatternList[index]
            if (index == size - 1) {
                return@matches (pathElement.value.inCurlyBrackets() && haveSimilarType(userPatternElement.value, pathElement.value))
                        || (userPatternElement.value.isNotBlank() && pathElement.value.startsWith(userPatternElement.value))
            }
            if (!pathElement.value.inCurlyBrackets() && pathElement != userPatternElement) {
                return@matches false
            }
        }
        return false
    }

    private fun haveSimilarType(userPatternElement: String, originalElement: String) =
            (isDigit(originalElement) && userPatternElement.isNumeric()) || !isDigit(originalElement)

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
}