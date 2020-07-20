package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierList
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.viartemev.requestmapper.BoundType
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldHaveSize
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object SpringClassMappingAnnotationSpek : Spek({

    describe("RequestMapping") {
        context("values on annotation with annotated class") {
            val memberValue = mock<PsiLiteralExpression> {
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
                on { containingClass } doReturn clazz
            }

            it("should return one path") {
                SpringClassMappingAnnotation.fetchMappingsFromClass(psiMethod) shouldBeEqualTo listOf("api")
            }
            it("should return default INBOUND request type") {
                val boundMappingFromClass = SpringClassMappingAnnotation.fetchBoundMappingFromClass(psiMethod)
                boundMappingFromClass shouldHaveSize 1
                boundMappingFromClass.first() shouldBeEqualTo BoundType.INBOUND
            }
        }
    }
})
