package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.viartemev.requestmapper.BoundType

private const val SPRING_REQUEST_MAPPING_CLASS = "org.springframework.web.bind.annotation.RequestMapping"
private const val SPRING_OPEN_FEIGN_CLASS = "org.springframework.cloud.openfeign.FeignClient"
private const val SPRING_NETFLIX_FEIGN_CLASS = "org.springframework.cloud.netflix.feign.FeignClient"

interface SpringClassMappingAnnotation {

    companion object {

        private fun mappingAnnotation(psiAnnotation: PsiAnnotation): SpringClassMappingAnnotation {
            return when (psiAnnotation.qualifiedName) {
                SPRING_REQUEST_MAPPING_CLASS -> ClassRequestMapping(psiAnnotation)
                SPRING_OPEN_FEIGN_CLASS -> ClassFeignClientMapping(psiAnnotation)
                SPRING_NETFLIX_FEIGN_CLASS -> ClassFeignClientMapping(psiAnnotation)
                else -> ClassUnknownAnnotation()
            }
        }

        fun fetchMappingsFromClass(psiMethod: PsiMethod): List<String> {
            val classMapping = findClassAnnotations(psiMethod)
                ?.flatMap { mappingAnnotation(it).fetchClassMapping() } ?: emptyList()
            return if (classMapping.isEmpty()) listOf("") else classMapping
        }

        fun fetchBoundMappingFromClass(psiMethod: PsiMethod): Set<BoundType> {
            val classMapping = findClassAnnotations(psiMethod)
                ?.flatMap { mappingAnnotation(it).fetchBoundMappingFromClass() }
                ?.toSet() ?: emptySet()
            return if (classMapping.isEmpty()) setOf(BoundType.INBOUND) else classMapping
        }

        private fun findClassAnnotations(psiMethod: PsiMethod): List<PsiAnnotation>? {
            return psiMethod
                .containingClass
                ?.modifierList
                ?.annotations
                ?.filterNotNull()
        }
    }

    fun fetchClassMapping(): List<String>
    fun fetchBoundMappingFromClass(): Set<BoundType>
}
