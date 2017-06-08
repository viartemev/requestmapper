package com.viartemev.requestmapper

import com.intellij.ide.util.gotoByName.CustomMatcherModel
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.utils.contains
import com.viartemev.requestmapper.utils.isSimilar

class RequestMappingModel(project: Project) : FilteringGotoByModel<FileType>(project, arrayOf<ChooseByNameContributor>(RequestMappingContributor())), DumbAware, CustomMatcherModel {

    override fun filterValueFor(item: NavigationItem): FileType? {
        return null
    }

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
        if (!userPattern.contains(" ") && !userPattern.contains("/")) {
            return popupItem.contains(userPattern)
        }

        val requiredPath = userPattern.substring(
                userPattern.indexOf('/'),
                if (userPattern.indexOf('?') == -1) userPattern.length else userPattern.indexOf('?')
        )

        val itemList = popupItem.split("/").filter { it.isNotBlank() }
        val requiredPathList = requiredPath.split("/").filter { it.isNotBlank() }

        if (requiredPathList.isNotEmpty() && itemList.isNotEmpty() && contains(requiredPathList, 0, itemList, 0)) {
            return true
        }
        return isSimilar(requiredPathList, itemList)
    }

}
