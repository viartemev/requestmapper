package com.viartemev.requestmapper.annotations.micronaut

import com.intellij.psi.PsiAnnotation

class Patch(psiAnnotation: PsiAnnotation) : MicronautMappingAnnotation(psiAnnotation) {

    override fun extractMethod() = METHOD

    companion object {
        private const val METHOD = "PATCH"
    }
}
