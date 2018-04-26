package com.viartemev.requestmapper.annotations.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiPolyadicExpression

interface PsiAnnotationValueVisitor {

    fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue): List<String>

    fun visitPsiReferenceExpression(expression: PsiReferenceExpression): List<String>

    fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue): List<String>

    fun visitPsiBinaryExpression(expression: PsiBinaryExpression): List<String>

    fun visitPsiPolyadicExpression(expression: PsiPolyadicExpression): List<String>
}
