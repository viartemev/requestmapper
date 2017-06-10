package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiField
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.impl.java.stubs.index.JavaStaticMemberNameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.viartemev.requestmapper.utils.unquote
import java.util.*
import java.util.Objects.requireNonNull

class PsiReferenceExpressionExtractor(project: Project) : PsiAnnotationValueExtractor<PsiReferenceExpression> {
    private val project: Project = requireNonNull(project)

    override fun extract(value: PsiReferenceExpression): List<String> {
        return Optional.ofNullable(value.referenceName).map<List<String>> { referenceName ->
            JavaStaticMemberNameIndex.getInstance()
                    .get(referenceName, project, GlobalSearchScope.projectScope(project))
                    .filter { PsiField::class.java.isInstance(it) }
                    .map { PsiField::class.java.cast(it) }
                    .flatMap { m -> (m.children.toList()) }
                    .filter { PsiLiteralExpression::class.java.isInstance(it) }
                    .map { it.text.unquote() }
        }
                .orElse(emptyList<String>())

    }
}
