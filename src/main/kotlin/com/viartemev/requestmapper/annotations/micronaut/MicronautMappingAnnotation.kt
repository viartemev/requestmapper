package com.viartemev.requestmapper.annotations.micronaut

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation

abstract class MicronautMappingAnnotation(
    val psiAnnotation: PsiAnnotation
) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun extractMethod(): String
}
