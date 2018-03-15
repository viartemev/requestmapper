package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>) = this.copy(pathElements = pathElements.map { it.addPathVariableType(parametersNameWithType.getOrDefault(it.value.unquoteCurlyBrackets(), "String")) })

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    //TODO move to companion object
    fun isSimilarTo(anotherPath: Path): Boolean {
        val allElementsArePathVariables = this.pathElements.drop(1).all { it.isPathVariable }
        return isSimilarPaths(Path(this.pathElements.drop(1)), Path(anotherPath.pathElements.drop(1)), allElementsArePathVariables)
    }

    //TODO rename method
    private tailrec fun isSimilarPaths(path1: Path,
                                       path2: Path,
                                       allElementsArePathVariables: Boolean): Boolean {
        if (path1.pathElements.size < path2.pathElements.size) {
            return false
        }

        if (matches(path1.pathElements, path2.pathElements, allElementsArePathVariables)) {
            return true
        }

        return isSimilarPaths(Path(path1.pathElements.drop(1)), path2, allElementsArePathVariables)
    }

    //TODO rename method
    private fun matches(popupItemList: List<PathElement>,
                        userPatternList: List<PathElement>,
                        allElementsArePathVariables: Boolean): Boolean {
        val hasExactMatching = popupItemList.subList(0, userPatternList.size).any { !it.isPathVariable }
        val listsAreEquals = popupItemList
            .zip(userPatternList)
            .all { (popupElement, userElement) ->
                popupElement == userElement || popupElement.value.startsWith(userElement.value)
            }
        return listsAreEquals && (hasExactMatching || allElementsArePathVariables)
    }
}
