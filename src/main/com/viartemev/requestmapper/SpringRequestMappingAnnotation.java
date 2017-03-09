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
    private static final String DEFAULT_METHOD = "GET";

    private final PsiAnnotation psiAnnotation;
    private final PsiElement psiElement;

    public SpringRequestMappingAnnotation(PsiAnnotation psiAnnotation,
                                          PsiElement psiElement) {
        this.psiAnnotation = psiAnnotation;
        this.psiElement = psiElement;
    }

    public List<RequestMappingItem> values() {
        if (psiElement instanceof PsiMethod) {
            return fetchRequestMappingItem(psiAnnotation, (PsiMethod) psiElement);
        } else if (psiElement instanceof PsiClass) {
            return fetchRequestMappingItem(psiAnnotation, (PsiClass) psiElement);
        }
        return Collections.emptyList();
    }

    private List<RequestMappingItem> fetchRequestMappingItem(PsiAnnotation annotation, PsiMethod psiMethod) {
        List<String> classValues = new ArrayList<>();
        for (PsiAnnotation requestMappingAnnotation : fetchRequestMappingAnnotationsFromParentClass(psiMethod)) {
            classValues.addAll(fetchParameterFromAnnotation(requestMappingAnnotation, VALUE_PARAM));
        }

        List<String> urls = fetchParameterFromAnnotation(annotation, VALUE_PARAM);
        String method = fetchMethodFromAnnotation(annotation, METHOD_PARAM);
        List<RequestMappingItem> result = new ArrayList<>();
        for (String url : urls) {
            if (classValues.size() != 0) {
                for (String classValue : classValues) {
                    result.add(new RequestMappingItem(psiMethod, classValue + url, method));
                }
            } else {
                result.add(new RequestMappingItem(psiMethod, url, method));
            }
        }
        return result;
    }

    private PsiAnnotation[] fetchRequestMappingAnnotationsFromParentClass(PsiMethod psiMethod) {
        List<PsiAnnotation> requestMappingAnnotations = new ArrayList<>();
        PsiClass containingClass = psiMethod.getContainingClass();
        if (containingClass != null && containingClass.getModifierList() != null) {
            PsiAnnotation[] annotations = containingClass.getModifierList().getAnnotations();
            for (PsiAnnotation annotation : annotations) {
                if (annotation != null && Objects.equals(annotation.getQualifiedName(), SPRING_REQUEST_MAPPING_CLASS)) {
                    requestMappingAnnotations.add(annotation);
                }
            }
        }
        return requestMappingAnnotations.toArray(new PsiAnnotation[]{});
    }

    private String fetchMethodFromAnnotation(PsiAnnotation annotation, String parameter) {
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(parameter);
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText()) && !Objects.equals("{}", valueParam.getText())) {
            return valueParam.getText().replace("RequestMethod.", "");
        }
        return DEFAULT_METHOD;
    }

    private List<RequestMappingItem> fetchRequestMappingItem(PsiAnnotation annotation, PsiClass psiClass) {
        List<String> urls = fetchParameterFromAnnotation(annotation, VALUE_PARAM);
        return urls.stream().map(url -> new RequestMappingItem(psiClass, unquote(url), EMPTY)).collect(toList());
    }

    private List<String> fetchParameterFromAnnotation(PsiAnnotation annotation, String parameter) {
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(parameter);
        if (valueParam instanceof PsiArrayInitializerMemberValue) {
            PsiAnnotationMemberValue[] members = ((PsiArrayInitializerMemberValue) valueParam).getInitializers();
            return Stream.of(members).map(PsiElement::getText).map(this::unquote).collect(toList());
        }
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText())) {
            return Collections.singletonList(unquote(valueParam.getText()));
        }
        return Collections.emptyList();
    }

    private String unquote(String s) {
        return s != null && s.length() >= 2 && s.charAt(0) == '\"'
                ? s.substring(1, s.length() - 1)
                : s;
    }
}
