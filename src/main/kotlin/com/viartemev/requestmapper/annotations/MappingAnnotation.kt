package com.viartemev.requestmapper.annotations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.spring.*

interface MappingAnnotation {

    fun values(): List<RequestMappingItem>

    companion object {
        fun mappingAnnotation(annotationName: String,
                              project: Project,
                              psiAnnotation: PsiAnnotation,
                              psiElement: PsiElement): MappingAnnotation {
            return when (annotationName) {
                RequestMapping::class.java.simpleName -> RequestMapping(psiAnnotation, psiElement, project)
                GetMapping::class.java.simpleName -> GetMapping(psiAnnotation, psiElement, project)
                PostMapping::class.java.simpleName -> PostMapping(psiAnnotation, psiElement, project)
                PutMapping::class.java.simpleName -> PutMapping(psiAnnotation, psiElement, project)
                PatchMapping::class.java.simpleName -> PatchMapping(psiAnnotation, psiElement, project)
                DeleteMapping::class.java.simpleName -> DeleteMapping(psiAnnotation, psiElement, project)
                else -> UnknownAnnotation.instance
            }
        }
    }

}
