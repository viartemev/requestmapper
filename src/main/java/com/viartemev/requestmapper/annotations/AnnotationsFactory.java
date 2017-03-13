package com.viartemev.requestmapper.annotations;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.viartemev.requestmapper.annotations.spring.*;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AnnotationsFactory {

    @NotNull
    public static MappingAnnotation createAnnotation(@Nullable String annotationName,
                                                     PsiAnnotation psiAnnotation,
                                                     PsiElement psiElement) {
        if (StringUtils.isEmpty(annotationName)) {
            return UnknownAnnotation.getInstance();
        }
        if (Objects.equals(annotationName, RequestMapping.class.getSimpleName())) {
            return new RequestMapping(psiAnnotation, psiElement);
        } else if (Objects.equals(annotationName, GetMapping.class.getSimpleName())) {
            return new GetMapping(psiAnnotation, psiElement);
        } else if (Objects.equals(annotationName, PostMapping.class.getSimpleName())) {
            return new PostMapping(psiAnnotation, psiElement);
        } else if (Objects.equals(annotationName, PutMapping.class.getSimpleName())) {
            return new PutMapping(psiAnnotation, psiElement);
        } else if (Objects.equals(annotationName, DeleteMapping.class.getSimpleName())) {
            return new DeleteMapping(psiAnnotation, psiElement);
        }
        return UnknownAnnotation.getInstance();
    }
}
