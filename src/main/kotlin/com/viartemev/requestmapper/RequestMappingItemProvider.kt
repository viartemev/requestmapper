package com.viartemev.requestmapper

import com.intellij.ide.util.gotoByName.*
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.psi.codeStyle.NameUtil
import com.intellij.util.CollectConsumer
import com.intellij.util.Processor
import com.intellij.util.SmartList
import com.intellij.util.SynchronizedCollectConsumer
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.FindSymbolParameters
import com.viartemev.requestmapper.model.PopupPath
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap
import kotlin.text.MatchResult

open class RequestMappingItemProvider : ChooseByNameItemProvider {

    override fun filterNames(base: ChooseByNameViewModel, names: Array<out String>, pattern: String): List<String> {
        return emptyList()
    }

    override fun filterElements(
        base: ChooseByNameViewModel,
        pattern: String,
        everywhere: Boolean,
        indicator: ProgressIndicator,
        consumer: Processor<Any>
    ): Boolean {
        if (base.project != null) {
            base.project!!.putUserData(ChooseByNamePopup.CURRENT_SEARCH_PATTERN, pattern)
        }
        val searchScope = FindSymbolParameters.searchScopeFor(base.project, everywhere)
        val parameters = FindSymbolParameters(pattern, pattern, searchScope, null)

        val namesList = getSortedResults(base, pattern, indicator, parameters)
        indicator.checkCanceled()
        return processByNames(base, everywhere, indicator, consumer, namesList, parameters)
    }


    companion object {
        private fun getSortedResults(
            base: ChooseByNameViewModel, pattern: String, indicator: ProgressIndicator, parameters: FindSymbolParameters
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
                    }, parameters
                )
            }

            namesList.sortWith(compareBy { PopupPath(it) })

            indicator.checkCanceled()
            return namesList
        }

        private fun processByNames(
            base: ChooseByNameViewModel,
            everywhere: Boolean,
            indicator: ProgressIndicator,
            consumer: Processor<Any>,
            namesList: List<String>,
            parameters: FindSymbolParameters
        ): Boolean {
            val sameNameElements: MutableList<Any> = SmartList()
            val qualifierMatchResults: MutableMap<Any, MatchResult> = Reference2ObjectOpenHashMap()
            val model = base.model
            for (name in namesList) {
                indicator.checkCanceled()
                val elements =
                    if (model is ContributorsBasedGotoByModel) model.getElementsByName(name, parameters, indicator)
                    else model.getElementsByName(name, everywhere, parameters.completePattern)
                if (elements.size > 1) {
                    sameNameElements.clear()
                    qualifierMatchResults.clear()
                    for (element in elements) {
                        indicator.checkCanceled()
                        sameNameElements.add(element)
                    }
                    if (!ContainerUtil.process(sameNameElements, consumer)) return false
                } else if (elements.size == 1) {
                    if (!consumer.process(elements[0])) return false
                }
            }
            return true
        }

        private fun matches(name: String?, pattern: String): Boolean {

            if (name == null) {
                return false
            }
            return try {
                if (pattern == "/") {
                    true
                } else {
                    val (_, path) = name.split(" ", limit = 2)
                    NameUtil.buildMatcher("*$pattern", NameUtil.MatchingCaseSensitivity.NONE).matches(path)
                }
            } catch (e: Exception) {
                false // no matches appears valid result for "bad" pattern
            }
        }
    }

}
