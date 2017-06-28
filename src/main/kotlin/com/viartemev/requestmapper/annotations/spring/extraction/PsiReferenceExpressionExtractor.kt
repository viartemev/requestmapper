package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.impl.java.stubs.index.JavaFieldNameIndex
import com.viartemev.requestmapper.utils.unquote
import java.util.*

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> {
        return Optional.ofNullable(value.referenceName).
                map { referenceName ->
                    val fields = JavaFieldNameIndex.getInstance().get(referenceName, value.project, value.resolveScope)
                    fields.
                            flatMap { field -> field.children.toList() }.
                            filter { it is PsiLiteralExpression }.
                            map { it.text.unquote() }
                }
                .orElse(emptyList<String>())
    }

}
