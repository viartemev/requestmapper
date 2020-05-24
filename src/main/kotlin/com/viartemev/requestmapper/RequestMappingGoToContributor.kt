package com.viartemev.requestmapper

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class RequestMappingGoToContributor(event: AnActionEvent) : AbstractGotoSEContributor(event) {

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return RequestMappingGoToContributor(initEvent)
        }
    }

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        return RequestMappingModel(project)
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
