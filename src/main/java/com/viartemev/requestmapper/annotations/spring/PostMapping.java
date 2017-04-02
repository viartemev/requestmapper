package com.viartemev.requestmapper.annotations.spring;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.viartemev.requestmapper.RequestMappingItem;

import java.util.Collections;
import java.util.List;

public class PostMapping extends RequestMapping {
    private static final String METHOD = "POST";

    public PostMapping(PsiAnnotation psiAnnotation, PsiElement psiElement, Project project) {
        super(psiAnnotation, psiElement, project);
    }

    @Override
    public List<RequestMappingItem> values() {
        if (psiElement instanceof PsiMethod) {
            return fetchRequestMappingItem(psiAnnotation, (PsiMethod) psiElement, METHOD);
        }
        return Collections.emptyList();
    }

}
