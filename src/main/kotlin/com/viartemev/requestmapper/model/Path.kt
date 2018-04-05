package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>): Path {
        return Path(pathElements.map {
            val key = it.value.unquoteCurlyBrackets().substringBefore(':')
            it.addPathVariableType(parametersNameWithType.getOrDefault(key, "Object"))
        })
    }

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Path

        if (pathElements != other.pathElements) return false

        return true
    }

    override fun hashCode(): Int {
        return pathElements.hashCode()
    }

    companion object {

        fun isSubpathOf(sourcePath: Path, targetPath: Path): Boolean {
            val sourcePathElements = sourcePath.pathElements.drop(1)
            val targetPathElements = targetPath.pathElements.drop(1)
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
                    .all { (popupElement, userElement) ->
                        popupElement == userElement || popupElement.value.startsWith(userElement.value)
                    }

            if (pathElementsAreEqual && (hasExactMatching || allSourceElementsArePathVariables)) {
                return true
            }

            return containsAll(sourcePathElements.drop(1), targetPathElements, allSourceElementsArePathVariables)
        }
    }
}
