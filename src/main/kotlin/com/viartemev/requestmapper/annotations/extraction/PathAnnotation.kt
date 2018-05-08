package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiReferenceExpression

class PathAnnotation(private val annotation: PsiAnnotation) {

    fun fetchMappings(parameter: String): List<String> {
        return object : BasePsiAnnotationValueVisitor() {
            override fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue) =
                    PsiArrayInitializerMemberValueExtractor().extract(arrayAValue)

            override fun visitPsiReferenceExpression(expression: PsiReferenceExpression) =
                    PsiReferenceExpressionExtractor().extract(expression)

            override fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue) =
                    PsiAnnotationMemberValueExtractor().extract(value)

            override fun visitPsiBinaryExpression(expression: PsiBinaryExpression) =
                    PsiBinaryExpressionExtractor().extract(expression)

            override fun visitPsiPolyadicExpression(expression: PsiPolyadicExpression) =
                    PsiPolyadicExpressionExtractor().extract(expression)
        }.visit(annotation, parameter)
    }
}
