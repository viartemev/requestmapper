package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.RequestMappingModel
import com.viartemev.requestmapper.contributors.CustomFilterWrapperMappingContributor
import com.viartemev.requestmapper.extensions.Extensions

class OutboundRequestMappingContributor(event: AnActionEvent) : AbstractGotoSEContributor(event) {

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        return RequestMappingModel(project, getContributors())
    }

    private fun getContributors(): List<ChooseByNameContributor> {
        return Extensions.getContributors().map {
            CustomFilterWrapperMappingContributor(
                it,
                { requestMappingItem -> requestMappingItem.boundType.contains(BoundType.OUTBOUND) }
            )
        }
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
            return OutboundRequestMappingContributor(initEvent)
        }
    }
}
