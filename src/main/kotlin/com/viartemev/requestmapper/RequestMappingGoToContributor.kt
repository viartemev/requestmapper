package com.viartemev.requestmapper

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class RequestMappingGoToContributor(event: AnActionEvent, private val action: GoToRequestMappingAction) : AbstractGotoSEContributor(event) {

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            val action: GoToRequestMappingAction = ActionManager.getInstance().getAction("GoToRequestMapping") as GoToRequestMappingAction
            return RequestMappingGoToContributor(initEvent, action)
        }
    }

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        return action.getRequestMappingModel(project)
    }

    override fun getSortWeight(): Int {
        return 1000
    }

    override fun getGroupName(): String {
        return "Request mapping"
    }

    override fun showInFindResults(): Boolean {
        return false
    }
}
