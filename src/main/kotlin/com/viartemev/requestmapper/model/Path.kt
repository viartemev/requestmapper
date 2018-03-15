package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>) = this.copy(pathElements = pathElements.map { it.addPathVariableType(parametersNameWithType.getOrDefault(it.value.unquoteCurlyBrackets(), "String")) })

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    companion object {

        fun isSubPathOf(sourcePath: Path, targetPath: Path): Boolean {
            val sourcePathElements = sourcePath.pathElements.drop(1)
            val targetPathElements = targetPath.pathElements.drop(1)
            val allSourceElementsArePathVariables = sourcePathElements.all { it.isPathVariable }

            return containAll(sourcePathElements, targetPathElements, allSourceElementsArePathVariables)
        }

        private tailrec fun containAll(sourcePathElements: List<PathElement>,
                                       targetPathElements: List<PathElement>,
                                       allSourceElementsArePathVariables: Boolean): Boolean {
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

            return containAll(sourcePathElements.drop(1), targetPathElements, allSourceElementsArePathVariables)
        }
    }
}
