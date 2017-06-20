package com.viartemev.requestmapper.annotations.spring

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem

import java.util.Collections

class GetMapping(psiAnnotation: PsiAnnotation, project: Project) : RequestMapping(psiAnnotation, project) {

    override fun values(): List<RequestMappingItem> {
        val psiElement = fetchAnnotatedPsiElement(psiAnnotation)
        return if (psiElement is PsiMethod) fetchRequestMappingItem(psiAnnotation, psiElement, METHOD) else emptyList()
    }

    companion object {
        private val METHOD = "GET"
    }

}
