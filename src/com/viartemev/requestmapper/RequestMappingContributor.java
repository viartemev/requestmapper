package com.viartemev.requestmapper;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.intellij.psi.search.GlobalSearchScope.projectScope;

public class RequestMappingContributor implements ChooseByNameContributor {

    private static final String SPRING_REQUEST_MAPPING_ANNOTATION = "RequestMapping";
    private static final String SPRING_REQUEST_MAPPING_VALUE_PARAM = "value";
    private static final String SPRING_REQUEST_MAPPING_METHOD_PARAM = "method";

    private static final List<String> httpMethodList = Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        String[] annotations = findAnnotations(project, SPRING_REQUEST_MAPPING_ANNOTATION);
        return annotations;
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<RequestMappingItem> requestMappingItems = findRequestMappingItems(project, SPRING_REQUEST_MAPPING_ANNOTATION);
        return requestMappingItems.toArray(new RequestMappingItem[requestMappingItems.size()]);
    }

    @NotNull
    private List<RequestMappingItem> findRequestMappingItems(Project project, String annotationName) {
        List<RequestMappingItem> requestMappingItems = new ArrayList<>();
        Collection<PsiAnnotation> annotations = JavaAnnotationIndex.getInstance().get(annotationName, project, projectScope(project));
        for (PsiAnnotation annotation : annotations) {
            PsiElement annotatedElement = fetchAnnotatedPsiElement(annotation);
            if (annotatedElement instanceof PsiMethod) {
                requestMappingItems.add(fetchRequestMappingItem(annotation, (PsiMethod) annotatedElement));
            }
        }

        return requestMappingItems;
    }

    private String[] findAnnotations(Project project, String annotationName) {
        Collection<PsiAnnotation> annotations = JavaAnnotationIndex.getInstance().get(annotationName, project, projectScope(project));
        List<String> valueList = new ArrayList<>(annotations.size());
        for (PsiAnnotation annotation : annotations) {
            String value = fetchAnnotationValue(annotation);
            if (value != null) {
                valueList.add(value);
            }
        }
        return valueList.toArray(new String[valueList.size()]);
    }

    private RequestMappingItem fetchRequestMappingItem(PsiAnnotation annotation, PsiMethod psiMethod) {
        String url = null;
        String method = null;
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(SPRING_REQUEST_MAPPING_VALUE_PARAM);
        PsiAnnotationMemberValue methodParam = annotation.findAttributeValue(SPRING_REQUEST_MAPPING_METHOD_PARAM);
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText())) {
            url = valueParam.getText();
        }
        if (methodParam != null && StringUtils.isNotEmpty(methodParam.getText())) {
            method = methodParam.getText();
        }

        return new RequestMappingItem(psiMethod, StringUtils.defaultIfEmpty(url, ""), StringUtils.defaultIfEmpty(method, "GET"));
    }

    private PsiElement fetchAnnotatedPsiElement(PsiAnnotation psiAnnotation) {
        PsiElement parent;
        for (parent = psiAnnotation.getParent(); parent != null && !(parent instanceof PsiClass) && !(parent instanceof PsiMethod); parent = parent.getParent()) {
        }
        return parent;
    }


    private String fetchAnnotationValue(PsiAnnotation annotation) {
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(SPRING_REQUEST_MAPPING_VALUE_PARAM);

        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText())) {
            return valueParam.getText();
        }
        return null;
    }

}
