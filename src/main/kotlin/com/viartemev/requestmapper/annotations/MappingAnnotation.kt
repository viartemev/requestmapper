package com.viartemev.requestmapper.annotations

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.jaxrs.DELETE
import com.viartemev.requestmapper.annotations.jaxrs.GET
import com.viartemev.requestmapper.annotations.jaxrs.HEAD
import com.viartemev.requestmapper.annotations.jaxrs.OPTIONS
import com.viartemev.requestmapper.annotations.jaxrs.PATCH
import com.viartemev.requestmapper.annotations.jaxrs.POST
import com.viartemev.requestmapper.annotations.jaxrs.PUT
import com.viartemev.requestmapper.annotations.micronaut.Delete
import com.viartemev.requestmapper.annotations.micronaut.Get
import com.viartemev.requestmapper.annotations.micronaut.Head
import com.viartemev.requestmapper.annotations.micronaut.Options
import com.viartemev.requestmapper.annotations.micronaut.Patch
import com.viartemev.requestmapper.annotations.micronaut.Post
import com.viartemev.requestmapper.annotations.micronaut.Put
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
            GET::class.java.simpleName,
            PUT::class.java.simpleName,
            POST::class.java.simpleName,
            OPTIONS::class.java.simpleName,
            HEAD::class.java.simpleName,
            DELETE::class.java.simpleName,
            PATCH::class.java.simpleName,
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
                GET::class.java.simpleName -> GET(psiAnnotation)
                PUT::class.java.simpleName -> PUT(psiAnnotation)
                POST::class.java.simpleName -> POST(psiAnnotation)
                OPTIONS::class.java.simpleName -> OPTIONS(psiAnnotation)
                HEAD::class.java.simpleName -> HEAD(psiAnnotation)
                DELETE::class.java.simpleName -> DELETE(psiAnnotation)
                PATCH::class.java.simpleName -> PATCH(psiAnnotation)
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
