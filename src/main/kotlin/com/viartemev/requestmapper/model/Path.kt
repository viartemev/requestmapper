package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.utils.unquoteCurlyBrackets

data class Path(private val pathElements: List<PathElement>) {
    constructor(string: String) : this(string.split("/").map { PathElement(it) })

    fun addPathVariablesTypes(parametersNameWithType: Map<String, String>) = this.copy(pathElements = pathElements.map { it.addPathVariableType(parametersNameWithType.getOrDefault(it.value.unquoteCurlyBrackets(), "String")) })

    fun toFullPath() = pathElements.joinToString("/") { it.value }

    // @todo #57 rewrite isSimilarTo method
    fun isSimilarTo(anotherPath: Path): Boolean {
        val allElementsIsPathVariables = this.pathElements.drop(1).all { it.isPathVariable }
        return isSimilarPaths(Path(this.pathElements.drop(1)), Path(anotherPath.pathElements.drop(1)), allElementsIsPathVariables)
    }

    //TODO rewrite it
    private tailrec fun isSimilarPaths(path1: Path,
                                       path2: Path,
                                       allElementsIsPathVariables: Boolean): Boolean {
        if (path1.pathElements.size < path2.pathElements.size) {
            return false
        }

        val listMatches = matches(path1.pathElements, path2.pathElements, allElementsIsPathVariables)

        if (listMatches) {
            return true
        }

        return isSimilarPaths(Path(path1.pathElements.drop(1)), path2, allElementsIsPathVariables)
    }

    //TODO rewrite it
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
