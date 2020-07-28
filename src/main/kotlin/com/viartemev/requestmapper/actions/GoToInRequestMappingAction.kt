package com.viartemev.requestmapper.actions

import com.intellij.navigation.ChooseByNameContributor
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.contributors.CustomFilterWrapperMappingContributor
import com.viartemev.requestmapper.extensions.Extensions

class GoToInRequestMappingAction : AbstractGoToRequestMappingAction() {
    override fun getContributors(): List<ChooseByNameContributor> {
        return Extensions.getContributors().map {
            CustomFilterWrapperMappingContributor(it,
                { requestMappingItem -> requestMappingItem.boundType.contains(BoundType.INBOUND) }
            )
        }
    }
}
