package com.viartemev.requestmapper.annotations.spring.extraction

import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiArrayInitializerMemberValue
import com.intellij.psi.PsiReferenceExpression

interface PsiAnnotationValueVisitor {

    fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue): List<String>

    fun visitPsiReferenceExpression(expression: PsiReferenceExpression): List<String>

    fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue): List<String>
    
}
