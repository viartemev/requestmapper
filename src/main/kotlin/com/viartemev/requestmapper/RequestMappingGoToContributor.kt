package com.viartemev.requestmapper

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.extensions.Extensions

class RequestMappingGoToContributor(event: AnActionEvent) : AbstractGotoSEContributor(event) {

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        return RequestMappingModel(project, Extensions.getExtensions())
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

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return RequestMappingGoToContributor(initEvent)
        }
    }
}
