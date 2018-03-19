package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiReferenceExpression
import com.viartemev.requestmapper.annotations.extraction.PsiExpressionExtractor.extractPath

class PsiReferenceExpressionExtractor : PsiAnnotationValueExtractor<PsiReferenceExpression> {

    override fun extract(value: PsiReferenceExpression): List<String> = listOf(extractPath(value))
}
