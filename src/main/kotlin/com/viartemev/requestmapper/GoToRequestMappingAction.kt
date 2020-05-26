package com.viartemev.requestmapper

import com.intellij.ide.actions.GotoActionBase
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project

class GoToRequestMappingAction : GotoActionBase(), DumbAware {

    override fun gotoActionPerformed(e: AnActionEvent) {
        val project = e.getData(PROJECT) ?: return

        val requestMappingModel = getRequestMappingModel(project)
        showNavigationPopup(e, requestMappingModel, GoToRequestMappingActionCallback(), null, true, false)
    }

    fun getRequestMappingModel(project: Project): RequestMappingModel {
        val extensionPoints: ExtensionPointName<ChooseByNameContributor> = ExtensionPointName.create("com.viartemev.requestmapper.requestMappingContributor")
        return RequestMappingModel(project, extensionPoints.extensionList)
    }

    private class GoToRequestMappingActionCallback : GotoActionBase.GotoActionCallback<String>() {

        override fun elementChosen(popup: ChooseByNamePopup, element: Any) {
            if (element is RequestMappingItem && element.canNavigate()) {
                element.navigate(true)
            }
        }
    }
}
