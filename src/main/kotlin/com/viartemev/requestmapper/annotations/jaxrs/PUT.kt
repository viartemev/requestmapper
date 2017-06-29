package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation

class PUT(psiAnnotation: PsiAnnotation) : JaxRsMappingAnnotation(psiAnnotation) {

    override fun extractMethod() = METHOD

    companion object {
        private val METHOD = "PUT"
    }

}