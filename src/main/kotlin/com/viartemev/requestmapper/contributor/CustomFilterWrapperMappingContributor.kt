package com.viartemev.requestmapper.contributor

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.Processor
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import com.viartemev.requestmapper.RequestMappingItem

class CustomFilterWrapperMappingContributor(
    private var finder: RequestMappingByNameFinder,
    private val customFilter: (RequestMappingItem) -> Boolean = { true },
    private var navigationItems: List<RequestMappingItem> = emptyList()
) : ChooseByNameContributorEx {
    override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
        navigationItems = finder.findItems(scope)
            .filter(customFilter)

        val names = navigationItems
            .map { it.name }
            .distinct()
            .toTypedArray()

        ContainerUtil.process(names, processor)
    }

    override fun processElementsWithName(name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters) {
        val filteredNames = navigationItems
            .filter { it.name == name }
            .toTypedArray()
        ContainerUtil.process(filteredNames, processor)
    }
}
