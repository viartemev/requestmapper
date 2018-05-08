package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.annotations.UrlFormatter
import com.viartemev.requestmapper.utils.fetchAnnotatedMethod

abstract class JaxRsMappingAnnotation(
    psiAnnotation: PsiAnnotation,
    private val urlFormatter: UrlFormatter = JaxRsUrlFormatter
) : MappingAnnotation {
    private val psiMethod = psiAnnotation.fetchAnnotatedMethod()
    private val jaxRsClass = JaxRsAnnotatedClass(psiMethod.containingClass)
    private val jaxRsMethod = JaxRsAnnotatedMethod(psiMethod)

    override fun values() = listOf(RequestMappingItem(psiMethod, urlFormatter.format(jaxRsClass.fetchMappingFromClass(), jaxRsMethod.fetchMappingFromMethod()), extractMethod()))

    abstract fun extractMethod(): String

    companion object {
        const val PATH_ANNOTATION = "javax.ws.rs.Path"
        const val ATTRIBUTE_NAME = "value"
        const val PATH_PARAM_ANNOTATION = "javax.ws.rs.PathParam"
    }
}
