package com.viartemev.requestmapper.annotations.spring

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierList
import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiParameterList
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object SpringMappingAnnotationSpek : Spek({

    describe("RequestMapping") {
        on("values on annotation without anything") {
            val psiParameterList = mock<PsiParameterList> {
                on { parameters } doReturn emptyArray<PsiParameter>()
            }
            val psiMethod = mock<PsiMethod> {
                on { parameterList } doReturn psiParameterList
            }
            val annotation = mock<PsiAnnotation> {
                on { parent } doReturn psiMethod
            }
            val requestMapping = RequestMapping(annotation)
            it("should return one root mapping with default GET method") {
                requestMapping.values().size shouldEqual 1
                requestMapping.values()[0].name shouldEqual "GET /"
            }
        }

        on("values on annotation with annotated class") {
            val psiParameterList = mock<PsiParameterList> {
                on { parameters } doReturn emptyArray<PsiParameter>()
            }
            val memberValue = mock<PsiAnnotationMemberValue> {
                on { text } doReturn "api"
            }
            val mappingAnnotation = mock<PsiAnnotation> {
                on { qualifiedName } doReturn "org.springframework.web.bind.annotation.RequestMapping"
                on { findAttributeValue("path") } doReturn memberValue
            }
            val psiModifierList = mock<PsiModifierList> {
                on { annotations } doReturn arrayOf(mappingAnnotation)
            }
            val clazz = mock<PsiClass> {
                on { modifierList } doReturn psiModifierList
            }
            val psiMethod = mock<PsiMethod> {
                on { parameterList } doReturn psiParameterList
                on { containingClass } doReturn clazz
            }
            val annotation = mock<PsiAnnotation> {
                on { parent } doReturn psiMethod
            }
            val requestMapping = RequestMapping(annotation)
            it("should return one class mapping with default GET method") {
                requestMapping.values().size shouldEqual 1
                requestMapping.values()[0].name shouldEqual "GET /api"
            }
        }

        on("values on annotation with annotated class and method") {
            val psiParameterList = mock<PsiParameterList> {
                on { parameters } doReturn emptyArray<PsiParameter>()
            }
            val classMappingMemberValue = mock<PsiAnnotationMemberValue> {
                on { text } doReturn "api"
            }
            val classMappingAnnotation = mock<PsiAnnotation> {
                on { qualifiedName } doReturn "org.springframework.web.bind.annotation.RequestMapping"
                on { findAttributeValue("path") } doReturn classMappingMemberValue
            }
            val psiModifierList = mock<PsiModifierList> {
                on { annotations } doReturn arrayOf(classMappingAnnotation)
            }
            val clazz = mock<PsiClass> {
                on { modifierList } doReturn psiModifierList
            }
            val psiMethod = mock<PsiMethod> {
                on { parameterList } doReturn psiParameterList
                on { containingClass } doReturn clazz
            }
            val methodMappingMemberValue = mock<PsiAnnotationMemberValue> {
                on { text } doReturn "method"
            }
            val methodMappingAnnotation = mock<PsiAnnotation> {
                on { parent } doReturn psiMethod
                on { findAttributeValue("path") } doReturn methodMappingMemberValue
            }
            val requestMapping = RequestMapping(methodMappingAnnotation)
            it("should return one mapping with default GET method and path which contains class and method mapping") {
                requestMapping.values().size shouldEqual 1
                requestMapping.values()[0].name shouldEqual "GET /api/method"
            }
        }
    }
})
