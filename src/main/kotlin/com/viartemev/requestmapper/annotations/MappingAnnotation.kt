package com.viartemev.requestmapper.annotations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.spring.*
import java.util.Objects

interface MappingAnnotation {
    fun values(): List<RequestMappingItem>

    companion object {

        fun mappingAnnotation(project: Project,
                              annotationName: String?,
                              psiAnnotation: PsiAnnotation,
                              psiElement: PsiElement): MappingAnnotation {
            if (annotationName == RequestMapping::class.java.simpleName) {
                return RequestMapping(psiAnnotation, psiElement, project)
            } else if (annotationName == GetMapping::class.java.simpleName) {
                return GetMapping(psiAnnotation, psiElement, project)
            } else if (annotationName == PostMapping::class.java.simpleName) {
                return PostMapping(psiAnnotation, psiElement, project)
            } else if (annotationName == PutMapping::class.java.simpleName) {
                return PutMapping(psiAnnotation, psiElement, project)
            } else if (annotationName == PatchMapping::class.java.simpleName) {
                return PatchMapping(psiAnnotation, psiElement, project)
            } else if (annotationName == DeleteMapping::class.java.simpleName) {
                return DeleteMapping(psiAnnotation, psiElement, project)
            }
            return UnknownAnnotation.instance
        }
    }

}
