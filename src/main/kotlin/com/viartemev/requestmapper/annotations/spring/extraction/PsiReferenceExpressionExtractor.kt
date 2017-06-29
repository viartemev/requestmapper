package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.impl.java.stubs.index.JavaFieldNameIndex
import com.viartemev.requestmapper.utils.unquote

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> {
        return value.referenceName?.let {
            val fields = JavaFieldNameIndex.getInstance().get(it, value.project, value.resolveScope)
            fields.
                    asSequence().
                    flatMap { it.children.asSequence() }.
                    filterIsInstance<PsiLiteralExpression>().
                    map { it.text.unquote() }.
                    toList()
        } ?: listOf()
    }

}
