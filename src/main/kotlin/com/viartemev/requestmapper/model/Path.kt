package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>): Path {
        val elements = pathElements.map {
            val key = it.value.unquoteCurlyBrackets().substringBefore(':')
            it.addPathVariableType(parametersNameWithType.getOrDefault(key, "Object"))
        }
        return Path(elements)
    }

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    companion object {

        fun isSubpathOf(sourcePath: Path, targetPath: Path): Boolean {
            val popupElements = sourcePath.pathElements.drop(1)
            val searchPatterns = targetPath.pathElements.drop(1)

            return containsAll(popupElements, searchPatterns, popupElements.all { it.isPathVariable })
        }

        private tailrec fun containsAll(popupElements: List<PathElement>, searchPatterns: List<PathElement>, allSourceElementsArePathVariables: Boolean): Boolean {
            if (popupElements.size < searchPatterns.size) return false

            val hasExactMatching = popupElements.subList(0, searchPatterns.size).any { !it.isPathVariable }
            val pathElementsAreEqual = popupElements
                .zip(searchPatterns)
                .all { (popupElement, searchPattern) ->
                    popupElement.compareToSearchPattern(searchPattern) || popupElement.value.startsWith(searchPattern.value)
                }

            if (pathElementsAreEqual && (hasExactMatching || allSourceElementsArePathVariables)) return true

            return containsAll(popupElements.drop(1), searchPatterns, allSourceElementsArePathVariables)
        }
    }
}
