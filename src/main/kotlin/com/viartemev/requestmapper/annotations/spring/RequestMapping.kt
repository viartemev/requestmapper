package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation

open class RequestMapping(psiAnnotation: PsiAnnotation) : SpringMappingAnnotation(psiAnnotation) {

    override fun extractMethod(): String {
        val valueParam = psiAnnotation.findAttributeValue(METHOD_PARAM)
        if (valueParam != null && valueParam.text.isNotBlank() && "{}" != valueParam.text) {
            return valueParam.text.replace("RequestMethod.", "")
        }
        return DEFAULT_METHOD
    }

    companion object {
        private val METHOD_PARAM = "method"
        private val DEFAULT_METHOD = "GET"
    }
}
