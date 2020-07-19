package com.viartemev.requestmapper.actions

import com.intellij.ide.actions.GotoActionBase
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT
import com.intellij.openapi.project.DumbAware
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.RequestMappingModel

abstract class AbstractGoToRequestMappingAction : GotoActionBase(), DumbAware {

    override fun gotoActionPerformed(e: AnActionEvent) {
        val project = e.getData(PROJECT) ?: return
        val requestMappingModel = RequestMappingModel(project, getContributors())
        showNavigationPopup(e, requestMappingModel, GoToRequestMappingActionCallback(), null, true, false)
    }

    abstract fun getContributors(): List<ChooseByNameContributor>

    private class GoToRequestMappingActionCallback : GotoActionBase.GotoActionCallback<String>() {

        override fun elementChosen(popup: ChooseByNamePopup, element: Any) {
            if (element is RequestMappingItem && element.canNavigate()) {
                element.navigate(true)
            }
        }
    }
}
