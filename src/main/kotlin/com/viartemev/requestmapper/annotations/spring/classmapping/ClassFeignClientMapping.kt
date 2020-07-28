package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.annotations.PathAnnotation

const val URL = "url"
const val PATH = "path"
const val NAME = "name"

class ClassFeignClientMapping(val annotation: PsiAnnotation) : SpringClassMappingAnnotation {
    override fun fetchClassMapping(): List<String> {
        val name = PathAnnotation(annotation).fetchMappings(NAME).firstOrNull() ?: ""
        var url = PathAnnotation(annotation).fetchMappings(URL).firstOrNull() ?: ""
        val path = PathAnnotation(annotation).fetchMappings(PATH).firstOrNull() ?: ""

        // analogue from org.springframework.cloud.openfeign.FeignClientFactoryBean#getTarget
        if (url.isBlank()) {
            url = name
        }
        return listOf(url + getCleanPath(path))
    }

    // analogue from org.springframework.cloud.openfeign.FeignClientFactoryBean#cleanPath
    private fun getCleanPath(path: String): String {
        var normalizedPath = if (!path.startsWith("/")) "/$path" else path
        normalizedPath = if (normalizedPath.endsWith("/")) normalizedPath.substring(0, normalizedPath.length - 1) else normalizedPath
        return normalizedPath
    }

    override fun fetchBoundMappingFromClass(): Set<BoundType> {
        return setOf(BoundType.OUTBOUND)
    }
}
