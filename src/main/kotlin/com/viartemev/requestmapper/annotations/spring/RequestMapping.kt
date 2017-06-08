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
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.StringUtils.EMPTY
import java.util.*

open class RequestMapping(internal val psiAnnotation: PsiAnnotation,
                          internal val psiElement: PsiElement,
                          internal val project: Project) : MappingAnnotation {

    override fun values(): List<RequestMappingItem> {
        if (psiElement is PsiMethod) {
            return fetchRequestMappingItem(psiAnnotation, psiElement, fetchMethodFromAnnotation(psiAnnotation, METHOD_PARAM))
        } else if (psiElement is PsiClass) {
            return fetchRequestMappingItem(psiAnnotation, psiElement)
        }
        return emptyList()
    }

    internal fun fetchRequestMappingItem(annotation: PsiAnnotation, psiMethod: PsiMethod, method: String): List<RequestMappingItem> {
        val classMappings = ArrayList<String>()
        for (requestMappingAnnotation in fetchRequestMappingAnnotationsFromParentClass(psiMethod)) {
            classMappings.addAll(fetchMapping(requestMappingAnnotation))
        }

        val methodMappings = fetchMapping(annotation)
        val result = ArrayList<RequestMappingItem>()
        for (url in methodMappings) {
            if (classMappings.size != 0) {
                for (classValue in classMappings) {
                    result.add(RequestMappingItem(psiMethod, classValue + url, method))
                }
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
            for (annotation in annotations) {
                if (annotation != null && annotation.qualifiedName == SPRING_REQUEST_MAPPING_CLASS) {
                    requestMappingAnnotations.add(annotation)
                }
            }
        }
        return requestMappingAnnotations.toTypedArray()
    }

    private fun fetchMethodFromAnnotation(annotation: PsiAnnotation, parameter: String): String {
        val valueParam = annotation.findAttributeValue(parameter)
        if (valueParam != null && StringUtils.isNotEmpty(valueParam.text) && "{}" != valueParam.text) {
            return valueParam.text.replace("RequestMethod.", "")
        }
        return DEFAULT_METHOD
    }

    private fun fetchRequestMappingItem(annotation: PsiAnnotation, psiClass: PsiClass): List<RequestMappingItem> {
        val classMappings = fetchMapping(annotation)
        return classMappings.map { url -> RequestMappingItem(psiClass, url.unquote(), EMPTY) }
    }

    private fun fetchMapping(annotation: PsiAnnotation): List<String> {
        val pathMapping = fetchMappingsFromAnnotation(annotation, PATH_PARAM)
        if (!pathMapping.isEmpty()) {
            return pathMapping
        }
        return fetchMappingsFromAnnotation(annotation, VALUE_PARAM)
    }

    private fun fetchMappingsFromAnnotation(annotation: PsiAnnotation, parameter: String): List<String> {
        return object : BasePsiAnnotationValueVisitor() {
            override fun visitPsiArrayInitializerMemberValue(memberValue: PsiArrayInitializerMemberValue): List<String> {
                return PsiArrayInitializerMemberValueExtractor().extract(memberValue)
            }

            override fun visitPsiReferenceExpression(expression: PsiReferenceExpression): List<String> {
                return PsiReferenceExpressionExtractor(project).extract(expression)
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
