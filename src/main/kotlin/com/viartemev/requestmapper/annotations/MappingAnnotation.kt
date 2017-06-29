package com.viartemev.requestmapper.annotations

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.jaxrs.*
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
                DeleteMapping::class.java.simpleName,
                Get::class.java.simpleName.toUpperCase(),
                Put::class.java.simpleName.toUpperCase(),
                Post::class.java.simpleName.toUpperCase(),
                Options::class.java.simpleName.toUpperCase(),
                Head::class.java.simpleName.toUpperCase(),
                Delete::class.java.simpleName.toUpperCase()
        )

        fun mappingAnnotation(annotationName: String,
                              psiAnnotation: PsiAnnotation): MappingAnnotation {
            return when (annotationName) {
                RequestMapping::class.java.simpleName -> RequestMapping(psiAnnotation)
                GetMapping::class.java.simpleName -> GetMapping(psiAnnotation)
                PostMapping::class.java.simpleName -> PostMapping(psiAnnotation)
                PutMapping::class.java.simpleName -> PutMapping(psiAnnotation)
                PatchMapping::class.java.simpleName -> PatchMapping(psiAnnotation)
                DeleteMapping::class.java.simpleName -> DeleteMapping(psiAnnotation)
                Get::class.java.simpleName.toUpperCase() -> Get(psiAnnotation)
                Put::class.java.simpleName.toUpperCase() -> Put(psiAnnotation)
                Post::class.java.simpleName.toUpperCase() -> Post(psiAnnotation)
                Options::class.java.simpleName.toUpperCase() -> Options(psiAnnotation)
                Head::class.java.simpleName.toUpperCase() -> Head(psiAnnotation)
                Delete::class.java.simpleName.toUpperCase() -> Delete(psiAnnotation)
                else -> UnknownAnnotation.instance
            }
        }
    }

}
