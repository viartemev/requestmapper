package com.viartemev.requestmapper

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotationMemberValue
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierList
import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiParameterList
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.viartemev.requestmapper.contributors.RequestMappingByRequestMappingItemFinder
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object RequestMappingFinderSpek : Spek({

    describe("RequestMappingByNameFinder") {
        context("findItems on empty navigationItems list") {
            it("should return empty list") {
                val contributor = object : RequestMappingByRequestMappingItemFinder() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        emptySequence()
                }
                contributor.findItems(DummyProject.getInstance()).size shouldBeEqualTo 0
            }
        }
        context("findItems on not method annotations") {
            it("should return empty list") {
                val annotationParent = mock<PsiElement> {}
                val psiAnnotation = mock<PsiAnnotation> {
                    on { parent } doReturn annotationParent
                }
                val contributor = object : RequestMappingByRequestMappingItemFinder() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        sequenceOf(psiAnnotation)
                }
                contributor.findItems(DummyProject.getInstance()).size shouldBeEqualTo 0
            }
        }
        context("findItems with one RequestMapping annotation") {
            it("should return one name of mapping") {
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
                val annotationSearcher: (String, Project) -> Sequence<PsiAnnotation> = { name: String, _ -> if (name == "RequestMapping") sequenceOf(annotation) else emptySequence() }
                val contributor = object : RequestMappingByRequestMappingItemFinder() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        annotationSearcher(annotationName, project)
                }
                val names = contributor.findItems(DummyProject.getInstance())
                names.size shouldBeEqualTo 1
                names[0].name shouldBeEqualTo "GET /api"
            }
        }
    }
})
