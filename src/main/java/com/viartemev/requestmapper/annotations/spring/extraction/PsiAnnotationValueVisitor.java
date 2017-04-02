package com.viartemev.requestmapper.annotations.spring.extraction;

import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PsiAnnotationValueVisitor {
    List<String> visitPsiArrayInitializerMemberValue(@NotNull PsiArrayInitializerMemberValue arrayAValue);

    List<String> visitPsiReferenceExpression(@NotNull PsiReferenceExpression expression);

    List<String> visitPsiAnnotationMemberValue(@NotNull PsiAnnotationMemberValue value);
}
