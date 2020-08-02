package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>): Path {
        return Path(
            pathElements.map {
                val key = it.value.unquoteCurlyBrackets().substringBefore(':')
                it.addPathVariableType(parametersNameWithType.getOrDefault(key, "Object"))
            }
        )
    }

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    companion object {

        fun isSubpathOf(sourcePath: Path, targetPath: Path): Boolean {
            var sourcePathElements = sourcePath.pathElements
            var targetPathElements = targetPath.pathElements

            // align by right if pattern is longer
            val subtractSizeOfPath = targetPathElements.size - sourcePathElements.size
            val addDrop = if (subtractSizeOfPath > 0) subtractSizeOfPath else 0

            sourcePathElements = sourcePathElements.drop(1)
            targetPathElements = targetPathElements.drop(1 + addDrop)
            val allSourceElementsArePathVariables = sourcePathElements.all { it.isPathVariable }

            return containsAll(sourcePathElements, targetPathElements, allSourceElementsArePathVariables)
        }

        private tailrec fun containsAll(sourcePathElements: List<PathElement>, targetPathElements: List<PathElement>, allSourceElementsArePathVariables: Boolean): Boolean {
            if (sourcePathElements.size < targetPathElements.size) {
                return false
            }

            val hasExactMatching = sourcePathElements.subList(0, targetPathElements.size).any { !it.isPathVariable }
            val pathElementsAreEqual = sourcePathElements
                .zip(targetPathElements)
                .all { (popupElement, searchPattern) ->
                    popupElement.compareToSearchPattern(searchPattern) || popupElement.value.startsWith(searchPattern.value)
                }

            if (pathElementsAreEqual && (hasExactMatching || allSourceElementsArePathVariables)) {
                return true
            }

            return containsAll(sourcePathElements.drop(1), targetPathElements, allSourceElementsArePathVariables)
        }
    }
}
