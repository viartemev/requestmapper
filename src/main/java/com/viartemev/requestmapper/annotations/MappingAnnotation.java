package com.viartemev.requestmapper.annotations;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.viartemev.requestmapper.RequestMappingItem;
import com.viartemev.requestmapper.annotations.spring.DeleteMapping;
import com.viartemev.requestmapper.annotations.spring.GetMapping;
import com.viartemev.requestmapper.annotations.spring.PostMapping;
import com.viartemev.requestmapper.annotations.spring.PutMapping;
import com.viartemev.requestmapper.annotations.spring.RequestMapping;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public interface MappingAnnotation {
    List<RequestMappingItem> values();

    @NotNull
    static MappingAnnotation mappingAnnotation(Project project,
                                               @Nullable String annotationName,
                                               PsiAnnotation psiAnnotation,
                                               PsiElement psiElement) {
        if (StringUtils.isEmpty(annotationName)) {
            return UnknownAnnotation.getInstance();
        }
        if (Objects.equals(annotationName, RequestMapping.class.getSimpleName())) {
            return new RequestMapping(psiAnnotation, psiElement, project);
        } else if (Objects.equals(annotationName, GetMapping.class.getSimpleName())) {
            return new GetMapping(psiAnnotation, psiElement, project);
        } else if (Objects.equals(annotationName, PostMapping.class.getSimpleName())) {
            return new PostMapping(psiAnnotation, psiElement, project);
        } else if (Objects.equals(annotationName, PutMapping.class.getSimpleName())) {
            return new PutMapping(psiAnnotation, psiElement, project);
        } else if (Objects.equals(annotationName, DeleteMapping.class.getSimpleName())) {
            return new DeleteMapping(psiAnnotation, psiElement, project);
        }
        return UnknownAnnotation.getInstance();
    }

}
