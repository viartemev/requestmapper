package com.viartemev.requestmapper.annotations.spring.extraction;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiReferenceExpression;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

public abstract class BasePsiAnnotationValueVisitor implements PsiAnnotationValueVisitor {
    public List<String> visit(PsiAnnotation annotation, String parameter) {
        List<String> result = Collections.emptyList();
        PsiAnnotationMemberValue valueParam = annotation.findAttributeValue(parameter);
        if (valueParam instanceof PsiArrayInitializerMemberValue) {
            result = visitPsiArrayInitializerMemberValue((PsiArrayInitializerMemberValue) valueParam);
        } else if (valueParam instanceof PsiReferenceExpression) {
            result = visitPsiReferenceExpression((PsiReferenceExpression) valueParam);
        } else if (valueParam != null && StringUtils.isNotEmpty(valueParam.getText())) {
            result = visitPsiAnnotationMemberValue(valueParam);
        }
        return result;
    }
}
