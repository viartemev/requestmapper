package com.viartemev.requestmapper.annotations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.spring.*

interface MappingAnnotation {

    fun values(): List<RequestMappingItem>

    companion object {

        val supportedAnnotations = listOf<String>(
                RequestMapping::class.java.simpleName,
                GetMapping::class.java.simpleName,
                PostMapping::class.java.simpleName,
                PutMapping::class.java.simpleName,
                PatchMapping::class.java.simpleName,
                DeleteMapping::class.java.simpleName
        )

        fun mappingAnnotation(annotationName: String,
                              project: Project,
                              psiAnnotation: PsiAnnotation): MappingAnnotation {
            return when (annotationName) {
                RequestMapping::class.java.simpleName -> RequestMapping(psiAnnotation, project)
                GetMapping::class.java.simpleName -> GetMapping(psiAnnotation, project)
                PostMapping::class.java.simpleName -> PostMapping(psiAnnotation, project)
                PutMapping::class.java.simpleName -> PutMapping(psiAnnotation, project)
                PatchMapping::class.java.simpleName -> PatchMapping(psiAnnotation, project)
                DeleteMapping::class.java.simpleName -> DeleteMapping(psiAnnotation, project)
                else -> UnknownAnnotation.instance
            }
        }
    }

}
