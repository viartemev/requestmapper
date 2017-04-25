package com.viartemev.requestmapper;

import com.google.common.collect.ImmutableList;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.viartemev.requestmapper.annotations.MappingAnnotation;
import com.viartemev.requestmapper.annotations.spring.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.intellij.psi.search.GlobalSearchScope.projectScope;
import static com.viartemev.requestmapper.annotations.MappingAnnotation.mappingAnnotation;

public class RequestMappingContributor implements ChooseByNameContributor {

    private static final List<String> supportedAnnotations = ImmutableList.of(
            RequestMapping.class.getSimpleName(),
            GetMapping.class.getSimpleName(),
            PostMapping.class.getSimpleName(),
            PutMapping.class.getSimpleName(),
            PatchMapping.class.getSimpleName(),
            DeleteMapping.class.getSimpleName()
    );

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        return supportedAnnotations.stream()
                .flatMap(ann -> findRequestMappingItems(project, ann).stream())
                .map(RequestMappingItem::getName)
                .distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return new NavigationItem[]{supportedAnnotations.stream()
                .flatMap(ann -> findRequestMappingItems(project, ann).stream())
                .filter(it -> Objects.equals(it.getName(), name))
                .findFirst().orElse(null)};
    }

    @NotNull
    private List<RequestMappingItem> findRequestMappingItems(Project project, String annotationName) {
        List<RequestMappingItem> requestMappingItems = new ArrayList<>();
        Collection<PsiAnnotation> annotations = JavaAnnotationIndex.getInstance().get(annotationName, project, projectScope(project));
        for (PsiAnnotation annotation : annotations) {
            PsiElement annotatedElement = fetchAnnotatedPsiElement(annotation);
            MappingAnnotation mappingAnnotation = mappingAnnotation(project, annotationName, annotation, annotatedElement);
            requestMappingItems.addAll(mappingAnnotation.values());
        }
        return requestMappingItems;
    }

    private PsiElement fetchAnnotatedPsiElement(PsiAnnotation psiAnnotation) {
        PsiElement parent;
        for (parent = psiAnnotation.getParent();
             parent != null && !(parent instanceof PsiClass) && !(parent instanceof PsiMethod);
             parent = parent.getParent()) {
        }
        return parent;
    }

}
