package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.SearchEverywhereBaseAction
import com.intellij.openapi.actionSystem.AnActionEvent

class OutBoundRequestMappingAction : SearchEverywhereBaseAction() {
    override fun actionPerformed(e: AnActionEvent) {
        showInSearchEverywherePopup(OutboundRequestMappingContributor::class.java.simpleName, e, true, false)
    }
}
