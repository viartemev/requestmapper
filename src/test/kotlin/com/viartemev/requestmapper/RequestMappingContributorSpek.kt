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
import com.viartemev.requestmapper.contributor.RequestMappingByNameContributor
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object RequestMappingContributorSpek : Spek({

    describe("RequestMappingContributor") {
        context("getItemsByName on empty navigationItems list") {
            it("should return empty list") {
                val contributor = object : RequestMappingByNameContributor() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        emptySequence()
                }
                contributor.getItemsByName("name", "pattern", DummyProject.getInstance(), false).size shouldBeEqualTo 0
            }
        }
        context("getItemsByName with 2 mapping items") {
            it("should return item a particular name") {
                val psiElement = mock<PsiElement> {}
                val navigationItems = listOf(
                    RequestMappingItem(psiElement, "/api/v1/users", "GET"),
                    RequestMappingItem(psiElement, "/api/v2/users", "GET")
                )
                val contributor = object : RequestMappingByNameContributor(navigationItems) {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        emptySequence()
                }
                val itemsByName = contributor.getItemsByName("GET /api/v1/users", "pattern", DummyProject.getInstance(), false)
                itemsByName.size shouldBeEqualTo 1
                itemsByName[0].name shouldBeEqualTo "GET /api/v1/users"
            }
        }
        context("getNames on empty navigationItems list") {
            it("should return empty list") {
                val contributor = object : RequestMappingByNameContributor() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        emptySequence()
                }
                contributor.getNames(DummyProject.getInstance(), false).size shouldBeEqualTo 0
            }
        }
        context("getNames on not method annotations") {
            it("should return empty list") {
                val annotationParent = mock<PsiElement> {}
                val psiAnnotation = mock<PsiAnnotation> {
                    on { parent } doReturn annotationParent
                }
                val contributor = object : RequestMappingByNameContributor() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        sequenceOf(psiAnnotation)
                }
                contributor.getNames(DummyProject.getInstance(), false).size shouldBeEqualTo 0
            }
        }
        context("getNames with one RequestMapping annotation") {
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
                val contributor = object : RequestMappingByNameContributor() {
                    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> =
                        annotationSearcher(annotationName, project)
                }
                val names = contributor.getNames(DummyProject.getInstance(), false)
                names.size shouldBeEqualTo 1
                names[0] shouldBeEqualTo "GET /api"
            }
        }
    }
})
