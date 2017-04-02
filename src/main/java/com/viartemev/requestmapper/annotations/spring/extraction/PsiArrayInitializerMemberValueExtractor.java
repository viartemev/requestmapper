package com.viartemev.requestmapper.annotations.spring.extraction;

import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiElement;
import com.viartemev.requestmapper.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PsiArrayInitializerMemberValueExtractor implements
        PsiAnnotationValueExtractor<PsiArrayInitializerMemberValue> {
    @Override
    public List<String> extract(@NotNull PsiArrayInitializerMemberValue value) {
        PsiAnnotationMemberValue[] members = value.getInitializers();
        return Stream.of(members).map(PsiElement::getText).map(CommonUtils::unquote).collect(toList());

    }
}
