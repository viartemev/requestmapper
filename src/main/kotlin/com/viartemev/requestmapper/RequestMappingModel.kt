package com.viartemev.requestmapper

import com.intellij.ide.util.gotoByName.CustomMatcherModel
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.utils.inCurlyBrackets

class RequestMappingModel(project: Project) : FilteringGotoByModel<FileType>(project, arrayOf<ChooseByNameContributor>(RequestMappingContributor())), DumbAware, CustomMatcherModel {
    override fun filterValueFor(item: NavigationItem): FileType? = null

    override fun getPromptText(): String = "Enter mapping url"

    override fun getNotInMessage(): String = "No matches found"

    override fun getNotFoundMessage(): String = "Mapping not found"

    override fun getCheckBoxName(): String? = null

    override fun getCheckBoxMnemonic(): Char = 0.toChar()

    override fun loadInitialCheckBoxState(): Boolean = false

    override fun saveInitialCheckBoxState(state: Boolean) = Unit

    override fun getSeparators(): Array<String> = emptyArray()

    override fun getFullName(element: Any): String? = getElementName(element)

    override fun willOpenEditor(): Boolean = false

    override fun matches(popupItem: String, userPattern: String): Boolean {
        return if (userPattern == "/") {
            true
        } else if (!userPattern.contains('/')) {
            userPattern in popupItem
        } else {
            isSimilarUrlPaths(popupItem, userPattern)
        }
    }

    private fun isSimilarUrlPaths(popupItem: String, userPattern: String): Boolean {
        val popupItemList = popupItem
                .split('/')
                .drop(1) //drop method name

        val userPatternList = userPattern
                .split('/')

        return isSimilarLists(popupItemList, if (userPatternList.first().isEmpty()) userPatternList.drop(1) else userPatternList)
    }

    private tailrec fun isSimilarLists(popupItemList: List<String>,
                                       userPatternList: List<String>,
                                       matches: Boolean = false): Boolean {
        if (matches) {
            return true
        }
        if (popupItemList.size < userPatternList.size) {
            return false
        }
        val listMatches = matches(popupItemList, userPatternList)

        return isSimilarLists(popupItemList.drop(1), userPatternList, listMatches)
    }

    private fun matches(popupItemList: List<String>,
                        userPatternList: List<String>): Boolean {
        val popupItemIterator = popupItemList.iterator()
        val userPatternIterator = userPatternList.iterator()

        while (popupItemIterator.hasNext()) {
            if (userPatternIterator.hasNext()) {
                val popupElement = popupItemIterator.next()
                val userPatternElement = userPatternIterator.next()
                if (!userPatternIterator.hasNext()) {
                    return popupElement.inCurlyBrackets() || (userPatternElement.isNotBlank() && popupElement.startsWith(userPatternElement))
                }
                if (!popupElement.inCurlyBrackets() && popupElement != userPatternElement) {
                    return false
                }
            } else {
                return false
            }
        }
        return true
    }

}
