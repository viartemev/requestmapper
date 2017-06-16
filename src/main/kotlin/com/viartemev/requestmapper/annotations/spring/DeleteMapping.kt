package com.viartemev.requestmapper.annotations.spring

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem

class DeleteMapping(psiAnnotation: PsiAnnotation, psiElement: PsiElement, project: Project) : RequestMapping(psiAnnotation, psiElement, project) {

    override fun values(): List<RequestMappingItem> {
        return if (psiElement is PsiMethod) fetchRequestMappingItem(psiAnnotation, psiElement, METHOD) else emptyList()
    }

    companion object {
        private val METHOD = "DELETE"
    }

}
