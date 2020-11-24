package com.viartemev.requestmapper

import com.intellij.ide.actions.searcheverywhere.FoundItemDescriptor
import com.intellij.ide.util.gotoByName.ChooseByNameModelEx
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.ide.util.gotoByName.ChooseByNameViewModel
import com.intellij.ide.util.gotoByName.ContributorsBasedGotoByModel
import com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiElement
import com.intellij.util.CollectConsumer
import com.intellij.util.Processor
import com.intellij.util.SmartList
import com.intellij.util.SynchronizedCollectConsumer
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import com.viartemev.requestmapper.model.Path
import com.viartemev.requestmapper.model.PopupPath
import com.viartemev.requestmapper.model.RequestedUserPath

open class RequestMappingItemProvider(context: PsiElement?) : DefaultChooseByNameItemProvider(context) {

    override fun filterElementsWithWeights(
        base: ChooseByNameViewModel,
        parameters: FindSymbolParameters,
        indicator: ProgressIndicator,
        consumer: Processor<in FoundItemDescriptor<*>?>
    ): Boolean {
        return ProgressManager.getInstance().computePrioritized<Boolean, RuntimeException> { filter(base, parameters.completePattern, indicator, consumer) }
    }

    private fun filter(base: ChooseByNameViewModel, pattern: String, indicator: ProgressIndicator, consumer: Processor<in FoundItemDescriptor<*>?>): Boolean {
        if (base.project != null) {
            base.project!!.putUserData(ChooseByNamePopup.CURRENT_SEARCH_PATTERN, pattern)
        }
        val idFilter: IdFilter? = null
        val searchScope = FindSymbolParameters.searchScopeFor(base.project, false)
        val parameters = FindSymbolParameters(pattern, pattern, searchScope, idFilter)

        val namesList = getSortedResults(base, pattern, indicator, parameters)
        indicator.checkCanceled()
        return processByNames(base, indicator, consumer, namesList, parameters)
    }

    companion object {
        private fun getSortedResults(
            base: ChooseByNameViewModel,
            pattern: String,
            indicator: ProgressIndicator,
            parameters: FindSymbolParameters
        ): List<String> {
            if (pattern.isEmpty() && !base.canShowListForEmptyPattern()) {
                return emptyList()
            }
            val namesList: MutableList<String> = ArrayList()
            val collect: CollectConsumer<String> = SynchronizedCollectConsumer(namesList)
            val model = base.model
            if (model is ChooseByNameModelEx) {
                indicator.checkCanceled()
                model.processNames(
                    { sequence: String? ->
                        indicator.checkCanceled()
                        if (matches(sequence, pattern)) {
                            collect.consume(sequence)
                            return@processNames true
                        }
                        return@processNames false
                    },
                    parameters
                )
            }

            namesList.sortWith(compareBy { PopupPath(it) })

            indicator.checkCanceled()
            return namesList
        }

        private fun processByNames(
            base: ChooseByNameViewModel,
            indicator: ProgressIndicator,
            consumer: Processor<in FoundItemDescriptor<*>?>,
            namesList: List<String>,
            parameters: FindSymbolParameters
        ): Boolean {
            val sameNameElements: MutableList<Any> = SmartList()
            val model = base.model
            for (name in namesList) {
                indicator.checkCanceled()
                val elements = if (model is ContributorsBasedGotoByModel) model.getElementsByName(name, parameters, indicator) else model.getElementsByName(name, false, parameters.completePattern)
                if (elements.size > 1) {
                    sameNameElements.clear()
                    for (element in elements) {
                        indicator.checkCanceled()
                        sameNameElements.add(element)
                    }
                    val processedItems: List<FoundItemDescriptor<*>> = ContainerUtil.map(sameNameElements) {
                        FoundItemDescriptor(it, 0)
                    }

                    if (!ContainerUtil.process(processedItems, consumer)) return false
                } else if (elements.size == 1) {
                    if (!consumer.process(FoundItemDescriptor(elements[0], 0))) return false
                }
            }
            return true
        }

        fun matches(name: String?, pattern: String): Boolean {
            if (name == null) {
                return false
            }
            return try {
                if (pattern == "/") {
                    true
                } else if (!pattern.contains('/')) {
                    val (method, path) = name.split(" ", limit = 2)
                    path.contains(pattern) || method.contains(pattern, ignoreCase = true)
                } else {
                    val popupPath = PopupPath(name)
                    val requestedUserPath = RequestedUserPath(pattern)
                    Path.isSubpathOf(
                        popupPath.toPath(),
                        requestedUserPath.toPath()
                    )
                }
            } catch (e: Exception) {
                false // no matches appears valid result for "bad" pattern
            }
        }
    }
}
