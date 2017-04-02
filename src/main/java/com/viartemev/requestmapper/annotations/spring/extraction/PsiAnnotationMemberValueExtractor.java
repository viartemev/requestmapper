package com.viartemev.requestmapper.annotations.spring.extraction;

import com.intellij.psi.PsiAnnotationMemberValue;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static com.viartemev.requestmapper.utils.CommonUtils.unquote;

public class PsiAnnotationMemberValueExtractor implements PsiAnnotationValueExtractor<PsiAnnotationMemberValue> {
    @Override
    public List<String> extract(@NotNull PsiAnnotationMemberValue value) {
        return StringUtils.isNotEmpty(value.getText()) ?
               Collections.singletonList(unquote(value.getText())) :
               Collections.emptyList();
    }
}
