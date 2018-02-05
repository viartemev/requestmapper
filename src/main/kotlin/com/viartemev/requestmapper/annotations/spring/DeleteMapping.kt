package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation

class DeleteMapping(psiAnnotation: PsiAnnotation) : RequestMapping(psiAnnotation) {

    override fun extractMethod() = METHOD

    companion object {
        private const val METHOD = "DELETE"
    }
}
