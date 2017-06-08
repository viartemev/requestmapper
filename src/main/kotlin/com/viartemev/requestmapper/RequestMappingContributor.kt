package com.viartemev.requestmapper

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.GlobalSearchScope.projectScope
import com.viartemev.requestmapper.annotations.MappingAnnotation.Companion.mappingAnnotation
import com.viartemev.requestmapper.annotations.spring.*
import java.util.*

class RequestMappingContributor : ChooseByNameContributor {

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String?> {
        return supportedAnnotations.
                flatMap { ann -> findRequestMappingItems(project, ann) }.
                map { it.name }.
                distinct().
                toTypedArray()
    }

    override fun getItemsByName(name: String, pattern: String, project: Project, includeNonProjectItems: Boolean): Array<NavigationItem> {
        return arrayOf(supportedAnnotations.
                flatMap { ann -> findRequestMappingItems(project, ann) }.
                filter({ it -> it.name == name })
                .first())
    }

    private fun findRequestMappingItems(project: Project, annotationName: String): List<RequestMappingItem> {
        val requestMappingItems = ArrayList<RequestMappingItem>()
        val annotations = JavaAnnotationIndex.getInstance().get(annotationName, project, projectScope(project))
        for (annotation in annotations) {
            val annotatedElement = fetchAnnotatedPsiElement(annotation)
            val mappingAnnotation = mappingAnnotation(project, annotationName, annotation, annotatedElement)
            requestMappingItems.addAll(mappingAnnotation.values())
        }
        return requestMappingItems
    }

    private fun fetchAnnotatedPsiElement(psiAnnotation: PsiAnnotation): PsiElement {
        var parent: PsiElement
        parent = psiAnnotation.parent
        while (parent != null && parent !is PsiClass && parent !is PsiMethod) {
            parent = parent.parent
        }
        return parent
    }

    companion object {

        private val supportedAnnotations = listOf<String>(
                RequestMapping::class.java.simpleName,
                GetMapping::class.java.simpleName,
                PostMapping::class.java.simpleName,
                PutMapping::class.java.simpleName,
                PatchMapping::class.java.simpleName,
                DeleteMapping::class.java.simpleName
        )
    }

}
