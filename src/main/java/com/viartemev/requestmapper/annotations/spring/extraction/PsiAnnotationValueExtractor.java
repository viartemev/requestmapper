package com.viartemev.requestmapper.annotations.spring.extraction;

import com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PsiAnnotationValueExtractor<T extends PsiAnnotationMemberValue> {
    List<String> extract(@NotNull T value);
}
