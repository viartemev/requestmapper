package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.*
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.annotations.spring.extraction.BasePsiAnnotationValueVisitor
import com.viartemev.requestmapper.annotations.spring.extraction.PsiAnnotationMemberValueExtractor
import com.viartemev.requestmapper.annotations.spring.extraction.PsiArrayInitializerMemberValueExtractor
import com.viartemev.requestmapper.annotations.spring.extraction.PsiReferenceExpressionExtractor
import com.viartemev.requestmapper.utils.fetchAnnotatedElement

abstract class SpringMappingAnnotation(val psiAnnotation: PsiAnnotation) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        return fetchRequestMappingItem(psiAnnotation, psiAnnotation.fetchAnnotatedElement() as PsiMethod, extractMethod())
    }

    abstract fun extractMethod(): String

    fun fetchRequestMappingItem(annotation: PsiAnnotation, psiMethod: PsiMethod, methodName: String): List<RequestMappingItem> {
        val classMappings = fetchRequestMappingAnnotationsFromParentClass(psiMethod)
        val methodMappings = fetchMapping(annotation)
        return classMappings.map { clazz ->
            methodMappings.map { RequestMappingItem(psiMethod, if (clazz.isBlank() && it.isBlank()) "/" else clazz + it, methodName) }
        }.flatten()
    }

    private fun fetchRequestMappingAnnotationsFromParentClass(psiMethod: PsiMethod): List<String> {
        val classMapping = psiMethod.
                containingClass?.
                modifierList?.
                annotations?.
                filterNotNull()?.
                filter { it.qualifiedName == SPRING_REQUEST_MAPPING_CLASS }?.
                flatMap { fetchMapping(it) } ?: emptyList()
        return if (classMapping.isEmpty()) listOf("") else classMapping
    }

    private fun fetchMapping(annotation: PsiAnnotation): List<String> {
        val pathMapping = fetchMappingsFromAnnotation(annotation, PATH_PARAM)
        return if (!pathMapping.isEmpty()) pathMapping else {
            val valueMapping = fetchMappingsFromAnnotation(annotation, VALUE_PARAM)
            if (valueMapping.isNotEmpty()) valueMapping else listOf("")
        }
    }

    private fun fetchMappingsFromAnnotation(annotation: PsiAnnotation, parameter: String): List<String> {
        return object : BasePsiAnnotationValueVisitor() {
            override fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue)
                    = PsiArrayInitializerMemberValueExtractor().extract(arrayAValue)

            override fun visitPsiReferenceExpression(expression: PsiReferenceExpression)
                    = PsiReferenceExpressionExtractor().extract(expression)

            override fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue)
                    = PsiAnnotationMemberValueExtractor().extract(value)
        }.visit(annotation, parameter)
    }

    companion object {
        private val VALUE_PARAM = "value"
        private val PATH_PARAM = "path"
        private val SPRING_REQUEST_MAPPING_CLASS = "org.springframework.web.bind.annotation.RequestMapping"
    }

}