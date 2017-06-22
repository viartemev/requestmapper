package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.utils.fetchAnnotatedElement

class PostMapping(psiAnnotation: PsiAnnotation) : RequestMapping(psiAnnotation) {

    override fun values(): List<RequestMappingItem> {
        return fetchRequestMappingItem(psiAnnotation, psiAnnotation.fetchAnnotatedElement() as PsiMethod, METHOD)
    }

    companion object {
        private val METHOD = "POST"
    }

}
