package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation

class HEAD(psiAnnotation: PsiAnnotation) : JaxRsMappingAnnotation(psiAnnotation) {

    override fun extractMethod() = METHOD

    companion object {
        private const val METHOD = "HEAD"
    }
}