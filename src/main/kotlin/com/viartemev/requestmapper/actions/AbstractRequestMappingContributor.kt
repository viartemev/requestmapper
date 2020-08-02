package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.util.NavigationItemListCellRenderer
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.RequestMappingModel
import javax.swing.ListCellRenderer

abstract class AbstractRequestMappingContributor(event: AnActionEvent) : AbstractGotoSEContributor(event) {

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        return RequestMappingModel(project, getContributors())
    }

    abstract fun getContributors(): List<ChooseByNameContributor>

    override fun showInFindResults(): Boolean {
        return false
    }

    override fun getElementsRenderer(): ListCellRenderer<Any> {
        return NavigationItemListCellRenderer()
    }
}
