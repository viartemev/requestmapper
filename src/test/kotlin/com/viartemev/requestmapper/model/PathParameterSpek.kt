package com.viartemev.requestmapper.model

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiModifierList
import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiType
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PathParameterSpek : Spek({

    describe("PathParameter") {
        context("extractParameterNameWithType on PsiParameter without annotations") {
            it("should return null") {
                val annotationsList = mock<PsiModifierList> {
                    on { annotations } doReturn emptyArray<PsiAnnotation>()
                }
                val psiType = mock<PsiType> {
                    on { presentableText } doReturn "variable"
                }
                val mock = mock<PsiParameter> {
                    on { modifierList } doReturn annotationsList
                    on { type } doReturn psiType
                }
                PathParameter(mock).extractParameterNameWithType("PathVariable", { _: PsiAnnotation, _: String -> "42" }).shouldBeNull()
            }
        }
        context("extractParameterNameWithType on PsiParameter with PathParam annotation") {
            it("should return pair annotation name-type") {
                val annotationName = "PathVariable"
                val pathAnnotation = mock<PsiAnnotation> {
                    on { qualifiedName } doReturn annotationName
                }
                val annotationsList = mock<PsiModifierList> {
                    on { annotations } doReturn arrayOf(pathAnnotation)
                }
                val psiType = mock<PsiType> {
                    on { presentableText } doReturn "long"
                }
                val mock = mock<PsiParameter> {
                    on { modifierList } doReturn annotationsList
                    on { type } doReturn psiType
                    on { name } doReturn "Long"
                }
                PathParameter(mock).extractParameterNameWithType(annotationName, { _: PsiAnnotation, _: String -> "id" }) shouldEqual Pair("id", "long")
            }
        }
    }
})
