package com.viartemev.requestmapper.contributors

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.mappingAnnotation
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.supportedAnnotations
import com.viartemev.requestmapper.utils.isMethodAnnotation

abstract class RequestMappingByRequestMappingItemFinder : RequestMappingItemFinder {

    abstract fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation>

    override fun findItems(project: Project?): List<RequestMappingItem> {
        if (project == null) return emptyList()
        return supportedAnnotations
            .flatMap { annotation -> findRequestMappingItems(project, annotation) }
    }

    private fun findRequestMappingItems(project: Project, annotationName: String): List<RequestMappingItem> {
        return getAnnotationSearchers(annotationName, project)
            .filterNotNull()
            .filter { it.isMethodAnnotation() }
            .map { annotation -> mappingAnnotation(annotationName, annotation) }
            .flatMap { mappingAnnotation -> mappingAnnotation.values().asSequence() }
            .toList()
    }
}
