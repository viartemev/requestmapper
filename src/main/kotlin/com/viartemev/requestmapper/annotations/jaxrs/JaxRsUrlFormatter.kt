package com.viartemev.requestmapper.annotations.jaxrs

import com.viartemev.requestmapper.utils.dropFirstEmptyStringIfExists

object JaxRsUrlFormatter {

    fun format(classMapping: String, methodMapping: String): String {
        val classPathSeq = classMapping.splitToSequence('/').filterNot { it.isBlank() }
        val methodPathList = methodMapping.split('/').dropFirstEmptyStringIfExists()
        return (classPathSeq + methodPathList).joinToString(separator = "/", prefix = "/")
    }
}
