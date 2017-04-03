package com.viartemev.requestmapper;

import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;

public class GoToRequestMappingAction extends GotoActionBase implements DumbAware {

    @Override
    protected void gotoActionPerformed(AnActionEvent e) {
        Project project = e.getData(PROJECT);
        if (project == null) {
            return;
        }

        showNavigationPopup(e, new RequestMappingModel(project), new GoToRequestMappingActionCallback(), false);
    }

    private static final class GoToRequestMappingActionCallback extends GotoActionCallback<String> {
        @Override
        public void elementChosen(ChooseByNamePopup popup, Object element) {
            if (!(element instanceof RequestMappingItem)) {
                return;
            }

            RequestMappingItem item = (RequestMappingItem) element;
            if (item.canNavigate()) {
                item.navigate(true);
            }
        }
    }
}
