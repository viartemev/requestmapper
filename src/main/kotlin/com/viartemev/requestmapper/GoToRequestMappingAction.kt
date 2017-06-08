package com.viartemev.requestmapper

import com.intellij.ide.actions.GotoActionBase
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT
import com.intellij.openapi.project.DumbAware

class GoToRequestMappingAction : GotoActionBase(), DumbAware {

    override fun gotoActionPerformed(e: AnActionEvent) {
        val project = e.getData(PROJECT) ?: return

        showNavigationPopup(e, RequestMappingModel(project), GoToRequestMappingActionCallback(), false)
    }

    private class GoToRequestMappingActionCallback : GotoActionBase.GotoActionCallback<String>() {

        override fun elementChosen(popup: ChooseByNamePopup, element: Any) {
            if (element !is RequestMappingItem) {
                return
            }

            if (element.canNavigate()) {
                element.navigate(true)
            }
        }

    }
}
