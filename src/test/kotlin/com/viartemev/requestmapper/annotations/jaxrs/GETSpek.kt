package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.*
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object GETSpek : Spek({

    describe("GET") {
        context("values on annotation without anything") {
            val psiModifierList = mock<PsiModifierList> {
                on { annotations } doReturn emptyArray<PsiAnnotation>()
            }
            val psiParameterList = mock<PsiParameterList> {
                on { parameters } doReturn emptyArray<PsiParameter>()
            }
            val psiMethod = mock<PsiMethod> {
                on { modifierList } doReturn psiModifierList
                on { parameterList } doReturn psiParameterList
            }
            val annotation = mock<PsiAnnotation> {
                on { parent } doReturn psiMethod
            }
            val requestMapping = GET(annotation)
            it("should return one root mapping with default GET method") {
                requestMapping.values().size shouldBeEqualTo 1
                requestMapping.values()[0].name shouldBeEqualTo "GET /"
            }
        }

        context("values on annotation with annotated class") {
            val psiParameterList = mock<PsiParameterList> {
                on { parameters } doReturn emptyArray<PsiParameter>()
            }
            val memberValue = mock<PsiAnnotationMemberValue> {
                on { text } doReturn "api"
            }
            val mappingAnnotation = mock<PsiAnnotation> {
                on { qualifiedName } doReturn "javax.ws.rs.Path"
                on { findAttributeValue("value") } doReturn memberValue
            }
            val psiModifierList = mock<PsiModifierList> {
                on { annotations } doReturn arrayOf(mappingAnnotation)
            }
            val psiMethod = mock<PsiMethod> {
                on { modifierList } doReturn psiModifierList
                on { parameterList } doReturn psiParameterList
            }
            val annotation = mock<PsiAnnotation> {
                on { parent } doReturn psiMethod
            }
            val requestMapping = GET(annotation)
            it("should return one class mapping with default GET method") {
                requestMapping.values().size shouldBeEqualTo 1
                requestMapping.values()[0].name shouldBeEqualTo "GET /api"
            }
        }
    }
})
