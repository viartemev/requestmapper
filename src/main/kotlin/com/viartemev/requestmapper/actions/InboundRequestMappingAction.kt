package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.SearchEverywhereBaseAction
import com.intellij.openapi.actionSystem.AnActionEvent

class InboundRequestMappingAction : SearchEverywhereBaseAction() {
    override fun actionPerformed(e: AnActionEvent) {
        showInSearchEverywherePopup(InboundRequestMappingContributor::class.java.simpleName, e, true, false)
    }
}
