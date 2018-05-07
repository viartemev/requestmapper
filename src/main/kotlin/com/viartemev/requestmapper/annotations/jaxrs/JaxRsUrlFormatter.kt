package com.viartemev.requestmapper.annotations.jaxrs

import com.viartemev.requestmapper.annotations.UrlFormatter
import com.viartemev.requestmapper.utils.dropFirstEmptyStringIfExists

object JaxRsUrlFormatter : UrlFormatter {

    override fun format(classMapping: String, methodMapping: String, param: String): String {
        val classPathSeq = classMapping.splitToSequence('/').filterNot { it.isBlank() }
        val methodPathList = methodMapping.split('/').dropFirstEmptyStringIfExists()
        return (classPathSeq + methodPathList).joinToString(separator = "/", prefix = "/")
    }
}
