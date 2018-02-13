package com.viartemev.requestmapper.utils

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.util.PsiUtilCore

fun PsiAnnotation.isMethodAnnotation() = fetchAnnotatedPsiElement(this) is PsiMethod

fun PsiAnnotation.fetchAnnotatedMethod() = fetchAnnotatedPsiElement(this) as PsiMethod

private tailrec fun fetchAnnotatedPsiElement(psiElement: PsiElement): PsiElement {
    val parent: PsiElement = psiElement.parent ?: return PsiUtilCore.NULL_PSI_ELEMENT
    if (parent is PsiMethod || parent is PsiClass) {
        return parent
    }
    return fetchAnnotatedPsiElement(parent)
}
