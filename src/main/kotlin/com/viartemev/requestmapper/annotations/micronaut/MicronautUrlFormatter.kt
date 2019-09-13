package com.viartemev.requestmapper.annotations.micronaut

import com.viartemev.requestmapper.annotations.UrlFormatter
import com.viartemev.requestmapper.utils.dropFirstEmptyStringIfExists

object MicronautUrlFormatter : UrlFormatter {

    override fun format(classMapping: String, methodMapping: String, param: String): String {
        val classPathSeq = classMapping.splitToSequence('/').filterNot { it.isBlank() }
        val methodPathList = methodMapping.split('/').dropFirstEmptyStringIfExists()
        return (classPathSeq + methodPathList).joinToString(separator = "/", prefix = "/")
    }
}
