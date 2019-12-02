package com.viartemev.requestmapper.annotations

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.jaxrs.Delete
import com.viartemev.requestmapper.annotations.jaxrs.Get
import com.viartemev.requestmapper.annotations.jaxrs.Head
import com.viartemev.requestmapper.annotations.jaxrs.Options
import com.viartemev.requestmapper.annotations.jaxrs.Patch
import com.viartemev.requestmapper.annotations.jaxrs.Post
import com.viartemev.requestmapper.annotations.jaxrs.Put
import com.viartemev.requestmapper.annotations.spring.DeleteMapping
import com.viartemev.requestmapper.annotations.spring.GetMapping
import com.viartemev.requestmapper.annotations.spring.PatchMapping
import com.viartemev.requestmapper.annotations.spring.PostMapping
import com.viartemev.requestmapper.annotations.spring.PutMapping
import com.viartemev.requestmapper.annotations.spring.RequestMapping

interface MappingAnnotation {

    fun values(): List<RequestMappingItem>

    companion object {
        val supportedAnnotations = listOf(
            RequestMapping::class.java.simpleName,
            GetMapping::class.java.simpleName,
            PostMapping::class.java.simpleName,
            PutMapping::class.java.simpleName,
            PatchMapping::class.java.simpleName,
            DeleteMapping::class.java.simpleName,
            Get::class.java.simpleName,
            Put::class.java.simpleName,
            Post::class.java.simpleName,
            Options::class.java.simpleName,
            Head::class.java.simpleName,
            Delete::class.java.simpleName,
            Patch::class.java.simpleName
        )

        fun mappingAnnotation(annotationName: String, psiAnnotation: PsiAnnotation): MappingAnnotation {
            return when (annotationName) {
                RequestMapping::class.java.simpleName -> RequestMapping(psiAnnotation)
                GetMapping::class.java.simpleName -> GetMapping(psiAnnotation)
                PostMapping::class.java.simpleName -> PostMapping(psiAnnotation)
                PutMapping::class.java.simpleName -> PutMapping(psiAnnotation)
                PatchMapping::class.java.simpleName -> PatchMapping(psiAnnotation)
                DeleteMapping::class.java.simpleName -> DeleteMapping(psiAnnotation)
                Get::class.java.simpleName -> Get(psiAnnotation)
                Put::class.java.simpleName -> Put(psiAnnotation)
                Post::class.java.simpleName -> Post(psiAnnotation)
                Options::class.java.simpleName -> Options(psiAnnotation)
                Head::class.java.simpleName -> Head(psiAnnotation)
                Delete::class.java.simpleName -> Delete(psiAnnotation)
                Patch::class.java.simpleName -> Patch(psiAnnotation)
                else -> UnknownAnnotation
            }
        }
    }
}
