package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation

class Options(psiAnnotation: PsiAnnotation) : JaxRsMappingAnnotation(psiAnnotation) {

    override fun extractMethod(): String {
        return METHOD
    }

    companion object {
        private val METHOD = "OPTIONS"
    }

}