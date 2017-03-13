package com.viartemev.requestmapper;

import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;

public class GoToRequestMappingAction extends GotoActionBase implements DumbAware {

    @Override
    protected void gotoActionPerformed(AnActionEvent e) {
        Project project = e.getData(PROJECT);
        if (project == null) {
            return;
        }

        RequestMappingModel requestMappingModel = new RequestMappingModel(project);
        GotoActionCallback<String> callback = new GotoActionCallback<String>() {

            @Override
            public void elementChosen(ChooseByNamePopup popup, Object element) {
                PsiElement navigationElement;
                if (!(element instanceof RequestMappingItem)) {
                    return;
                }
                RequestMappingItem item = (RequestMappingItem) element;
                if (item.getPsiElement() != null
                        && (navigationElement = item.getPsiElement().getNavigationElement()) != null
                        && navigationElement instanceof Navigatable
                        && ((Navigatable) navigationElement).canNavigate()) {
                    ((Navigatable) navigationElement).navigate(true);
                }

            }
        };
        showNavigationPopup(e, requestMappingModel, callback, false);
    }
}
