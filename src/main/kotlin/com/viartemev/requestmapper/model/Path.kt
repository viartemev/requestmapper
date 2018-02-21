package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>) =
            this.copy(pathElements = pathElements.map { it.addPathVariableType(parametersNameWithType.getOrDefault(it.value.unquoteCurlyBrackets(), "String")) })

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    // @todo #57 rewrite isSimilarTo method
    fun isSimilarTo(anotherPath: Path): Boolean {
        return isSimilarPaths(
                Path(this.pathElements.drop(1)),
                Path(anotherPath.pathElements.drop(1))
        )
    }

    //TODO rewrite it
    private tailrec fun isSimilarPaths(path1: Path,
                                       path2: Path,
                                       matches: Boolean = false): Boolean {
        if (matches) {
            return true
        }
        if (path1.pathElements.size < path2.pathElements.size) {
            return false
        }
        val listMatches = matches(path1.pathElements, path2.pathElements)

        return isSimilarPaths(Path(path1.pathElements.drop(1)), path2, listMatches)
    }

    //TODO rewrite it
    private fun matches(popupItemList: List<PathElement>,
                        userPatternList: List<PathElement>): Boolean {
        val size = userPatternList.size
        var hasExactMatching = false
        popupItemList.forEachIndexed { index, pathElement ->
            if (index == size) {
                return@matches false
            }
            val userPatternElement = userPatternList[index]
            if (index == size - 1) {
                val elementsEqual = userPatternElement == pathElement
                val elementsAreNotPathVariables = !userPatternElement.isPathVariable && !pathElement.isPathVariable
                val elementsPartiallyEqual = pathElement.value.startsWith(userPatternElement.value)
                if ((elementsEqual && elementsAreNotPathVariables) || (elementsPartiallyEqual && elementsAreNotPathVariables)) {
                    hasExactMatching = true
                }
                return@matches (elementsEqual || elementsPartiallyEqual) && hasExactMatching
            }
            if (pathElement != userPatternElement) {
                return@matches false
            }
            val elementsEqual = userPatternElement == pathElement
            if (elementsEqual && !userPatternElement.isPathVariable && !pathElement.isPathVariable) {
                hasExactMatching = true
            }
        }
        return false
    }
}