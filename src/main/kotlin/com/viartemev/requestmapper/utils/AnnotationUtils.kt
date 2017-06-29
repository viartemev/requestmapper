package com.viartemev.requestmapper.utils

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

fun PsiAnnotation.fetchAnnotatedElement(): PsiElement? {
    return fetchAnnotatedPsiElement(this)
}

private tailrec fun fetchAnnotatedPsiElement(psiElement: PsiElement): PsiElement? {
    val parent:PsiElement? = psiElement.parent
    if (parent == null || parent is PsiClass || parent is PsiMethod) {
        return parent
    }
    return fetchAnnotatedPsiElement(parent)
}
