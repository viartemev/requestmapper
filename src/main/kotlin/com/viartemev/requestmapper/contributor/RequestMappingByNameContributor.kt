package com.viartemev.requestmapper.contributor

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.mappingAnnotation
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.supportedAnnotations
import com.viartemev.requestmapper.utils.isMethodAnnotation

abstract class RequestMappingByNameContributor(
    private var navigationItems: List<RequestMappingItem> = emptyList()
) : ChooseByNameContributor {

    abstract fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation>

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        navigationItems = supportedAnnotations
            .flatMap { annotation -> findRequestMappingItems(project, annotation) }

        return navigationItems
            .map { it.name }
            .distinct()
            .toTypedArray()
    }

    override fun getItemsByName(name: String, pattern: String, project: Project, includeNonProjectItems: Boolean): Array<NavigationItem> {
        return navigationItems
            .filter { it.name == name }
            .toTypedArray()
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
