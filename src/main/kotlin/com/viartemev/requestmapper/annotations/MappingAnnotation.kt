package com.viartemev.requestmapper.annotations

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.RequestMappingItem
import com.viartemev.requestmapper.annotations.jaxrs.Delete as JaxRsDelete
import com.viartemev.requestmapper.annotations.jaxrs.Get as JaxRsGet
import com.viartemev.requestmapper.annotations.jaxrs.Head as JaxRsHead
import com.viartemev.requestmapper.annotations.jaxrs.Options as JaxRsOptions
import com.viartemev.requestmapper.annotations.jaxrs.Patch as JaxRsPatch
import com.viartemev.requestmapper.annotations.jaxrs.Post as JaxRsPost
import com.viartemev.requestmapper.annotations.jaxrs.Put as JaxRsPut

import com.viartemev.requestmapper.annotations.micronaut.Delete as MicronautDelete
import com.viartemev.requestmapper.annotations.micronaut.Get as MicronautGet
import com.viartemev.requestmapper.annotations.micronaut.Head as MicronautHead
import com.viartemev.requestmapper.annotations.micronaut.Options as MicronautOptions
import com.viartemev.requestmapper.annotations.micronaut.Patch as MicronautPatch
import com.viartemev.requestmapper.annotations.micronaut.Post as MicronautPost
import com.viartemev.requestmapper.annotations.micronaut.Put as MicronautPut

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

            JaxRsGet::class.java.simpleName,
            JaxRsPut::class.java.simpleName,
            JaxRsPost::class.java.simpleName,
            JaxRsOptions::class.java.simpleName,
            JaxRsHead::class.java.simpleName,
            JaxRsDelete::class.java.simpleName,
            JaxRsPatch::class.java.simpleName,

            MicronautDelete::class.java.simpleName,
            MicronautGet::class.java.simpleName,
            MicronautHead::class.java.simpleName,
            MicronautOptions::class.java.simpleName,
            MicronautPatch::class.java.simpleName,
            MicronautPost::class.java.simpleName,
            MicronautPut::class.java.simpleName
        )

        fun mappingAnnotation(annotationName: String, psiAnnotation: PsiAnnotation): MappingAnnotation {
            return when (annotationName) {
                RequestMapping::class.java.simpleName -> RequestMapping(psiAnnotation)
                GetMapping::class.java.simpleName -> GetMapping(psiAnnotation)
                PostMapping::class.java.simpleName -> PostMapping(psiAnnotation)
                PutMapping::class.java.simpleName -> PutMapping(psiAnnotation)
                PatchMapping::class.java.simpleName -> PatchMapping(psiAnnotation)
                DeleteMapping::class.java.simpleName -> DeleteMapping(psiAnnotation)

                JaxRsGet::class.java.simpleName -> JaxRsGet(psiAnnotation)
                JaxRsPut::class.java.simpleName -> JaxRsPut(psiAnnotation)
                JaxRsPost::class.java.simpleName -> JaxRsPost(psiAnnotation)
                JaxRsOptions::class.java.simpleName -> JaxRsOptions(psiAnnotation)
                JaxRsHead::class.java.simpleName -> JaxRsHead(psiAnnotation)
                JaxRsDelete::class.java.simpleName -> JaxRsDelete(psiAnnotation)
                JaxRsPatch::class.java.simpleName -> JaxRsPatch(psiAnnotation)

                MicronautGet::class.java.simpleName -> MicronautGet(psiAnnotation)
                MicronautPut::class.java.simpleName -> MicronautPut(psiAnnotation)
                MicronautPost::class.java.simpleName -> MicronautPost(psiAnnotation)
                MicronautOptions::class.java.simpleName -> MicronautOptions(psiAnnotation)
                MicronautHead::class.java.simpleName -> MicronautHead(psiAnnotation)
                MicronautDelete::class.java.simpleName -> MicronautDelete(psiAnnotation)
                MicronautPatch::class.java.simpleName -> MicronautPatch(psiAnnotation)

                else -> UnknownAnnotation
            }
        }
    }
}
