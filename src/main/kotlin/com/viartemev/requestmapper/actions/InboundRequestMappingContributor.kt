package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.contributors.CustomFilterWrapperMappingContributor
import com.viartemev.requestmapper.extensions.Extensions

class InboundRequestMappingContributor(event: AnActionEvent) : AbstractRequestMappingContributor(event) {

    override fun getContributors(): List<ChooseByNameContributor> {
        return Extensions.getContributors().map {
            CustomFilterWrapperMappingContributor(
                it,
                { requestMappingItem -> requestMappingItem.boundType.contains(BoundType.INBOUND) }
            )
        }
    }

    override fun getSortWeight(): Int {
        return 1000
    }

    override fun getGroupName(): String {
        return "In-requests"
    }

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return InboundRequestMappingContributor(initEvent)
        }
    }
}
