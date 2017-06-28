package com.viartemev.requestmapper

import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project

class RequestMappingModel(project: Project) : FilteringGotoByModel<FileType>(project, arrayOf<ChooseByNameContributor>(RequestMappingContributor())), DumbAware {

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

}
