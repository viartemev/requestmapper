package com.viartemev.requestmapper.annotations.spring

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem

class PutMapping(psiAnnotation: PsiAnnotation, project: Project) : RequestMapping(psiAnnotation, project) {

    override fun values(): List<RequestMappingItem> {
        val psiElement = fetchAnnotatedPsiElement(psiAnnotation)
        return if (psiElement is PsiMethod) return fetchRequestMappingItem(psiAnnotation, psiElement, METHOD) else emptyList()
    }

    companion object {
        private val METHOD = "PUT"
    }

}
