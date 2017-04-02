package com.viartemev.requestmapper.annotations.spring.extraction;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.impl.java.stubs.index.JavaStaticMemberNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.viartemev.requestmapper.utils.CommonUtils.unquote;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class PsiReferenceExpressionExtractor implements PsiAnnotationValueExtractor<PsiReferenceExpression> {
    private final Project project;

    public PsiReferenceExpressionExtractor(Project project) {
        this.project = requireNonNull(project);
    }

    @Override
    public List<String> extract(@NotNull PsiReferenceExpression expression) {
        return Optional.ofNullable(expression.getReferenceName()).map(
                referenceName ->
                        JavaStaticMemberNameIndex.getInstance()
                             .get(referenceName,
                                  project,
                                  GlobalSearchScope.projectScope(project))
                             .stream()
                             .filter(PsiField.class::isInstance)
                             .map(PsiField.class::cast)
                             .flatMap(m -> Stream.of(m.getChildren()))
                             .filter(PsiLiteralExpression.class::isInstance)
                             .map(c -> unquote(c.getText()))
                             .collect(toList()))
                        .orElse(Collections.emptyList());

    }
}
