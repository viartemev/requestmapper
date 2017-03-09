package com.viartemev.requestmapper;

import com.intellij.psi.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.EMPTY;

public class SpringRequestMappingAnnotation {
    private static final String VALUE_PARAM = "value";
    private static final String METHOD_PARAM = "method";
    private static final String SPRING_REQUEST_MAPPING_CLASS = "org.springframework.web.bind.annotation.RequestMapping";

    private final PsiAnnotation psiAnnotation;
    private final PsiElement psiElement;

    public SpringRequestMappingAnnotation(PsiAnnotation psiAnnotation,
                                          PsiElement psiElement) {
        this.psiAnnotation = psiAnnotation;
        this.psiElement = psiElement;
    }

    public List<RequestMappingItem> values() {
        List<RequestMappingItem> requestMappingItems = new ArrayList<>();
        if (psiElement instanceof PsiMethod) {
            requestMappingItems.addAll(fetchRequestMappingItem(psiAnnotation, (PsiMethod) psiElement));
        } else if (psiElement instanceof PsiClass) {
            requestMappingItems.addAll(fetchRequestMappingItem(psiAnnotation, (PsiClass) psiElement));
        }
        return requestMappingItems;
    }

    private List<RequestMappingItem> fetchRequestMappingItem(PsiAnnotation annotation, PsiMethod psiMethod) {
        List<String> classValues = new ArrayList<>();
        PsiClass containingClass = psiMethod.getContainingClass();
        if (containingClass != null && containingClass.getModifierList() != null) {
            PsiAnnotation[] annotations = containingClass.getModifierList().getAnnotations();
            for (PsiAnnotation an : annotations) {
                if (an != null && Objects.equals(an.getQualifiedName(), SPRING_REQUEST_MAPPING_CLASS)) {
                    classValues.addAll(fetchParameterFromAnnotation(an, VALUE_PARAM));
                }
            }
        }
        List<String> urls = fetchParameterFromAnnotation(annotation, VALUE_PARAM);
        String method = fetchParameterFromAnnotation(annotation, METHOD_PARAM, "GET");
        List<RequestMappingItem> result = new ArrayList<>();
        for (String url : urls) {
            for (String classValue : classValues) {
                result.add(new RequestMappingItem(psiMethod, unquote(classValue) + unquote(url), method));
            }
        }
        return result;
    }

    private String fetchParameterFromAnnotation(PsiAnnotation annotation, String parameter, String defaultValue) {
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(parameter);
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText()) && !Objects.equals("{}", valueParam.getText())) {
            return valueParam.getText().replace("RequestMethod.", "");
        }
        return defaultValue;
    }

    private List<RequestMappingItem> fetchRequestMappingItem(PsiAnnotation annotation, PsiClass psiClass) {
        List<String> urls = fetchParameterFromAnnotation(annotation, VALUE_PARAM);
        return urls.stream().map(url -> new RequestMappingItem(psiClass, unquote(url), EMPTY)).collect(toList());
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

    private String unquote(String s) {
        return s != null && s.length() >= 2 && s.charAt(0) == '\"'
                ? s.substring(1, s.length() - 1)
                : s;
    }


}
