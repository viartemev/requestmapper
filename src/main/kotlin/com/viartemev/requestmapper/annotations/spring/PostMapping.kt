package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation

class PostMapping(psiAnnotation: PsiAnnotation) : RequestMapping(psiAnnotation) {

    override fun extractMethod() = METHOD

    companion object {
        private val METHOD = "POST"
    }
}
