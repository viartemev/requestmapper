package com.viartemev.requestmapper

import com.intellij.ide.util.gotoByName.CustomMatcherModel
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.model.Path
import com.viartemev.requestmapper.model.PopupPath
import com.viartemev.requestmapper.model.RequestedUserPath

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
            val (method, path) = popupItem.split(" ", limit = 2)
            path.contains(userPattern) || method.contains(userPattern, ignoreCase = true)
        } else {
            Path.isSubPathOf(
                    PopupPath(popupItem).toPath(),
                    RequestedUserPath(userPattern).toPath()
            )
        }
    }
}
