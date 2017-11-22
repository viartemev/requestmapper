package com.viartemev.requestmapper

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.GlobalSearchScope.projectScope
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.mappingAnnotation
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.supportedAnnotations
import com.viartemev.requestmapper.utils.fetchAnnotatedElement

class RequestMappingContributor : ChooseByNameContributor {

    private var navigationItems: List<RequestMappingItem> = emptyList()

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return supportedAnnotations
                .flatMap { annotation -> findRequestMappingItems(project, annotation) }
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
        return JavaAnnotationIndex
                .getInstance()
                .get(annotationName, project, projectScope(project))
                .asSequence()
                .filter { annotation -> annotation.fetchAnnotatedElement() is PsiMethod }
                .filterNotNull()
                .map { annotation -> mappingAnnotation(annotationName, annotation) }
                .flatMap { mappingAnnotation -> mappingAnnotation.values().asSequence() }
                .toList()
    }

}
