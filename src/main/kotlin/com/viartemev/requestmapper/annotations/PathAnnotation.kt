package com.viartemev.requestmapper.annotations

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiReferenceExpression
import com.viartemev.requestmapper.annotations.extraction.BasePsiAnnotationValueVisitor
import com.viartemev.requestmapper.annotations.extraction.PsiAnnotationMemberValueExtractor
import com.viartemev.requestmapper.annotations.extraction.PsiArrayInitializerMemberValueExtractor
import com.viartemev.requestmapper.annotations.extraction.PsiReferenceExpressionExtractor

class PathAnnotation(private val annotation: PsiAnnotation) {

    fun fetchMappings(parameter: String): List<String> {
        return object : BasePsiAnnotationValueVisitor() {
            override fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue) =
                PsiArrayInitializerMemberValueExtractor().extract(arrayAValue)

            override fun visitPsiReferenceExpression(expression: PsiReferenceExpression) =
                PsiReferenceExpressionExtractor().extract(expression)

            override fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue) =
                PsiAnnotationMemberValueExtractor().extract(value)
        }.visit(annotation, parameter)
    }
}
