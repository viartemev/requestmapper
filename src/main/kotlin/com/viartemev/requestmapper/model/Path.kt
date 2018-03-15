package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>) = this.copy(pathElements = pathElements.map { it.addPathVariableType(parametersNameWithType.getOrDefault(it.value.unquoteCurlyBrackets(), "String")) })

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    //TODO move to companion object
    fun isSimilarTo(anotherPath: Path): Boolean {
        val allElementsArePathVariables = this.pathElements.drop(1).all { it.isPathVariable }
        return isSimilarPaths(this.pathElements.drop(1), anotherPath.pathElements.drop(1), allElementsArePathVariables)
    }

    //TODO rename method
    private tailrec fun isSimilarPaths(path1: List<PathElement>,
                                       path2: List<PathElement>,
                                       allElementsArePathVariables: Boolean): Boolean {
        if (path1.size < path2.size) {
            return false
        }

        val hasExactMatching = path1.subList(0, path2.size).any { !it.isPathVariable }
        val listsAreEquals = path1
                .zip(path2)
                .all { (popupElement, userElement) ->
                    popupElement == userElement || popupElement.value.startsWith(userElement.value)
                }

        if (listsAreEquals && (hasExactMatching || allElementsArePathVariables)) {
            return true
        }

        return isSimilarPaths(path1.drop(1), path2, allElementsArePathVariables)
    }
}
