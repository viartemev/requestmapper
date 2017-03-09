package com.viartemev.requestmapper;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.intellij.psi.search.GlobalSearchScope.projectScope;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class RequestMappingContributor implements ChooseByNameContributor {

    private static final String SPRING_REQUEST_MAPPING_ANNOTATION = "RequestMapping";
    private static final String SPRING_REQUEST_MAPPING_VALUE_PARAM = "value";
    private static final String SPRING_REQUEST_MAPPING_METHOD_PARAM = "method";
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
        return items.keySet().toArray(new String[items.size()]);
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
            if (annotatedElement instanceof PsiMethod) {
                requestMappingItems.addAll(fetchRequestMappingItem(annotation, (PsiMethod) annotatedElement));
            }
        }
        return requestMappingItems;
    }

    private List<RequestMappingItem> fetchRequestMappingItem(PsiAnnotation annotation, PsiMethod psiMethod) {
        List<String> urls = fetchParameterFromAnnotation(annotation, SPRING_REQUEST_MAPPING_VALUE_PARAM);
        String method = fetchParameterFromAnnotation(annotation, SPRING_REQUEST_MAPPING_METHOD_PARAM, "GET");
        return urls.stream().map(u -> new RequestMappingItem(psiMethod, u, method)).collect(toList());
    }

    private PsiElement fetchAnnotatedPsiElement(PsiAnnotation psiAnnotation) {
        PsiElement parent;
        for (parent = psiAnnotation.getParent(); parent != null && !(parent instanceof PsiClass) && !(parent instanceof PsiMethod); parent = parent.getParent()) {
        }
        return parent;
    }

    private String fetchParameterFromAnnotation(PsiAnnotation annotation, String parameter, String defaultValue) {
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(parameter);
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText())) {
            return valueParam.getText();
        }
        return defaultValue;
    }

    private List<String> fetchParameterFromAnnotation(PsiAnnotation annotation, String parameter) {
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(parameter);
        if (valueParam instanceof PsiArrayInitializerMemberValue) {
            PsiAnnotationMemberValue[] members = ((PsiArrayInitializerMemberValue) valueParam).getInitializers();
            return Stream.of(members).map(PsiElement::getText).collect(toList());
        }
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText())) {
            return Collections.singletonList(valueParam.getText());
        }
        return Collections.emptyList();
    }

}
