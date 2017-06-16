package com.viartemev.requestmapper.annotations.spring

import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation
import com.viartemev.requestmapper.annotations.spring.extraction.BasePsiAnnotationValueVisitor
import com.viartemev.requestmapper.annotations.spring.extraction.PsiAnnotationMemberValueExtractor
import com.viartemev.requestmapper.annotations.spring.extraction.PsiArrayInitializerMemberValueExtractor
import com.viartemev.requestmapper.annotations.spring.extraction.PsiReferenceExpressionExtractor
import com.viartemev.requestmapper.utils.unquote
import org.apache.commons.lang.StringUtils.EMPTY
import java.util.*

open class RequestMapping(internal val psiAnnotation: PsiAnnotation,
                          internal val psiElement: PsiElement,
                          internal val project: Project) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        return when (psiElement) {
            is PsiMethod -> fetchRequestMappingItem(psiAnnotation, psiElement, fetchMethodFromAnnotation(psiAnnotation, METHOD_PARAM))
            is PsiClass -> fetchRequestMappingItem(psiAnnotation, psiElement)
            else -> emptyList()
        }
    }

    internal fun fetchRequestMappingItem(annotation: PsiAnnotation, psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMappings = ArrayList<String>()
        for (requestMappingAnnotation in fetchRequestMappingAnnotationsFromParentClass(psiMethod)) {
            classMappings.addAll(fetchMapping(requestMappingAnnotation))
        }

        val methodMappings = fetchMapping(annotation)
        val result = ArrayList<RequestMappingItem>()
        for (url in methodMappings) {
            if (classMappings.isNotEmpty()) {
                classMappings.mapTo(result) { RequestMappingItem(psiMethod, it + url, method) }
            } else {
                result.add(RequestMappingItem(psiMethod, url, method))
            }
        }
        return result
    }

    private fun fetchRequestMappingAnnotationsFromParentClass(psiMethod: PsiMethod): Array<PsiAnnotation> {
        val requestMappingAnnotations = ArrayList<PsiAnnotation>()
        val containingClass = psiMethod.containingClass
        if (containingClass != null && containingClass.modifierList != null) {
            val annotations = containingClass.modifierList!!.annotations
            annotations.filterTo(requestMappingAnnotations) { it != null && it.qualifiedName == SPRING_REQUEST_MAPPING_CLASS }
        }
        return requestMappingAnnotations.toTypedArray()
    }

    private fun fetchMethodFromAnnotation(annotation: PsiAnnotation, parameter: String): String {
        val valueParam = annotation.findAttributeValue(parameter)
        if (valueParam != null && valueParam.text.isNotEmpty() && "{}" != valueParam.text) {
            return valueParam.text.replace("RequestMethod.", "")
        }
        return DEFAULT_METHOD
    }

    private fun fetchRequestMappingItem(annotation: PsiAnnotation, psiClass: PsiClass): List<RequestMappingItem> {
        return fetchMapping(annotation).map { RequestMappingItem(psiClass, it.unquote(), EMPTY) }
    }

    private fun fetchMapping(annotation: PsiAnnotation): List<String> {
        val pathMapping = fetchMappingsFromAnnotation(annotation, PATH_PARAM)
        return if (!pathMapping.isEmpty()) pathMapping else fetchMappingsFromAnnotation(annotation, VALUE_PARAM)
    }

    private fun fetchMappingsFromAnnotation(annotation: PsiAnnotation, parameter: String): List<String> {
        return object : BasePsiAnnotationValueVisitor() {
            override fun visitPsiArrayInitializerMemberValue(arrayAValue: PsiArrayInitializerMemberValue): List<String> {
                return PsiArrayInitializerMemberValueExtractor().extract(arrayAValue)
            }

            override fun visitPsiReferenceExpression(expression: PsiReferenceExpression): List<String> {
                val extract = PsiReferenceExpressionExtractor(project).extract(expression)
                return extract
            }

            override fun visitPsiAnnotationMemberValue(value: PsiAnnotationMemberValue): List<String> {
                return PsiAnnotationMemberValueExtractor().extract(value)
            }
        }.visit(annotation, parameter)
    }

    companion object {
        private val VALUE_PARAM = "value"
        private val PATH_PARAM = "path"
        private val METHOD_PARAM = "method"
        private val SPRING_REQUEST_MAPPING_CLASS = "org.springframework.web.bind.annotation.RequestMapping"
        private val DEFAULT_METHOD = "GET"
    }
}
