package com.viartemev.requestmapper;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

import static com.intellij.psi.search.GlobalSearchScope.projectScope;
import static java.util.stream.Collectors.toMap;

public class RequestMappingContributor implements ChooseByNameContributor {

    private static final String SPRING_REQUEST_MAPPING_ANNOTATION = "RequestMapping";
    private final Map<String, NavigationItem> items;

    public RequestMappingContributor() {
        this.items = new HashMap<>();
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        items.putAll(
                findRequestMappingItems(project, SPRING_REQUEST_MAPPING_ANNOTATION)
                        .stream()
                        .collect(toMap(RequestMappingItem::getName, Function.identity()))
        );
        return items
                .keySet()
                .toArray(new String[items.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return new NavigationItem[]{items.get(name)};
    }

    @NotNull
    private List<RequestMappingItem> findRequestMappingItems(Project project, String annotationName) {
        List<RequestMappingItem> requestMappingItems = new ArrayList<>();
        Collection<PsiAnnotation> annotations = JavaAnnotationIndex.getInstance().get(annotationName, project, projectScope(project));
        for (PsiAnnotation annotation : annotations) {
            PsiElement annotatedElement = fetchAnnotatedPsiElement(annotation);
            SpringRequestMappingAnnotation springRequestMappingAnnotation = new SpringRequestMappingAnnotation(annotation, annotatedElement);
            requestMappingItems.addAll(springRequestMappingAnnotation.values());
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
