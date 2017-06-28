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

    fun fetchRequestMappingItem(annotation: PsiAnnotation, psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMappings = fetchRequestMappingAnnotationsFromParentClass(psiMethod).
                flatMap { ann -> fetchMapping(ann) }

        return fetchMapping(annotation).
                flatMap { methodMapping ->
                    (if (classMappings.isEmpty()) listOf("") else classMappings).
                            map { classMapping -> RequestMappingItem(psiMethod, classMapping + methodMapping, method) }
                }
    }

    private fun fetchRequestMappingAnnotationsFromParentClass(psiMethod: PsiMethod): Array<PsiAnnotation> {
        return psiMethod.
                containingClass?.
                modifierList?.
                annotations?.
                filter { it != null && it.qualifiedName == SPRING_REQUEST_MAPPING_CLASS }?.
                toTypedArray() ?: emptyArray()
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
            override fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue): List<String> {
                return PsiArrayInitializerMemberValueExtractor().extract(arrayAValue)
            }

            override fun visitPsiReferenceExpression(expression: PsiReferenceExpression): List<String> {
                return PsiReferenceExpressionExtractor().extract(expression)
            }

            override fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue): List<String> {
                return PsiAnnotationMemberValueExtractor().extract(value)
            }
        }.visit(annotation, parameter)
    }

    companion object {
        private val VALUE_PARAM = "value"
        private val PATH_PARAM = "path"
        private val SPRING_REQUEST_MAPPING_CLASS = "org.springframework.web.bind.annotation.RequestMapping"
    }

}