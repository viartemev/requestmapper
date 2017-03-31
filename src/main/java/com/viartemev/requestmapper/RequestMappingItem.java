package com.viartemev.requestmapper;

import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

import javax.swing.*;
import java.util.Optional;

public class RequestMappingItem implements NavigationItem {
    private final PsiElement psiElement;
    private final String urlPath;
    private final Alias alias;
    private final RequestMappingItemPresentation mItemPresentation;

    public RequestMappingItem(PsiElement psiElement, String urlPath, String requestMethod) {
        this.psiElement = psiElement;
        this.urlPath = urlPath;
        this.alias = new Alias(psiElement, requestMethod);
        this.mItemPresentation = new RequestMappingItemPresentation();
    }

    public PsiElement getPsiElement() {
        return this.psiElement;
    }

    @Override
    public String getName() {
        return this.urlPath;
    }

    @Override
    public ItemPresentation getPresentation() {
        return this.mItemPresentation;
    }


    @Override
    public void navigate(boolean requestFocus) {
        navigationElement().ifPresent(navigatable -> navigatable.navigate(requestFocus));
    }

    @Override
    public boolean canNavigate() {
        return navigationElement().map(Navigatable::canNavigate).orElse(false);
    }

    private Optional<Navigatable> navigationElement() {
        PsiElement navigationElement;
        if (getPsiElement() != null
                && (navigationElement = getPsiElement().getNavigationElement()) != null
                && navigationElement instanceof Navigatable) {
            return Optional.of((Navigatable) navigationElement);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    private class RequestMappingItemPresentation implements ItemPresentation {

        private RequestMappingItemPresentation() {
            //empty ctor
        }

        @Override
        public String getPresentableText() {
            return RequestMappingItem.this.urlPath;
        }

        @Override
        public String getLocationString() {
            return RequestMappingItem.this.alias.getAlias();
        }

        @Override
        public Icon getIcon(boolean b) {
            return RequestMapperIcons.SEARCH;
        }
    }

    private class Alias {
        private final PsiElement psiElement;
        private final String requestMethod;

        public Alias(PsiElement psiElement, String requestMethod) {
            this.psiElement = psiElement;
            this.requestMethod = requestMethod;
        }

        public String getAlias() {
            if (psiElement instanceof PsiMethod) {
                PsiMethod psiElement = (PsiMethod) this.psiElement;
                PsiClass clss;
                PsiMethod method;
                return requestMethod + " " + ((clss = (method = psiElement).getContainingClass()) == null ? "" : clss.getName() + ".") + method.getName();
            } else if (psiElement instanceof PsiClass) {
                return ((PsiClass) psiElement).getName();
            } else {
                return "undefined";
            }
        }
    }

}
