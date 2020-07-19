package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.annotations.PathAnnotation

const val URL = "url"
const val PATH = "path"
const val NAME = "name"

class FeignClientMapping(val annotation: PsiAnnotation) : SpringClassMappingAnnotation {
    override fun fetchClassMapping(): List<String> {
        val name = getString(PathAnnotation(annotation).fetchMappings(NAME))
        var url = getString(PathAnnotation(annotation).fetchMappings(URL))
        val path = getString(PathAnnotation(annotation).fetchMappings(PATH))

        // analogue of org.springframework.cloud.openfeign.FeignClientFactoryBean#getTarget
        if (url.isBlank()) {
            url = name
        }
        return listOf(url + getCleanPath(path))
    }

    // analogue of org.springframework.cloud.openfeign.FeignClientFactoryBean#cleanPath
    private fun getCleanPath(path: String): String {
        var normalizedPath = if (!path.startsWith("/")) "/$path" else path
        normalizedPath = if (normalizedPath.endsWith("/")) normalizedPath.substring(0, normalizedPath.length - 1) else normalizedPath
        return normalizedPath
    }

    private fun getString(fetchMappings: List<String>): String {
        return if (fetchMappings.isEmpty()) "" else fetchMappings[0]
    }

    override fun fetchBoundMappingFromClass(): Set<BoundType> {
        return setOf(BoundType.OUTBOUND)
    }
}
