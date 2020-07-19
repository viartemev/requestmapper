package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.RequestMappingModel

class OutRequestMappingGoToContributor(event: AnActionEvent) : AbstractGotoSEContributor(event) {

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        return RequestMappingModel(project, GoToOutRequestMappingAction().getContributors())
    }

    override fun getSortWeight(): Int {
        return 2000
    }

    override fun getGroupName(): String {
        return "Outbound requests"
    }

    override fun showInFindResults(): Boolean {
        return false
    }

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return OutRequestMappingGoToContributor(initEvent)
        }
    }
}
