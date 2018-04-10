package com.viartemev.requestmapper

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethod
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object RequestMappingItemSpek : Spek({

    describe("RequestMappingItem") {
        on("getPresentation on class PsiElement") {
            val psiFile = mock<PsiFile> {
                on { name } doReturn "Controller"
            }
            val psiElement = mock<PsiClass> {
                on { containingFile } doReturn psiFile
                on { name } doReturn "Controller"
            }
            val item = RequestMappingItem(psiElement, "/api/v1/users", "POST")
            val presentation = item.presentation
            it("should return presentation with presentableText equal to the method with the path") {
                presentation.presentableText shouldEqual "POST /api/v1/users"
            }
            it("should return presentation with getLocationString equal to the method with the path") {
                presentation.locationString shouldEqual "Controller"
            }
            it("should return presentation with the RequestMapperIcons.SEARCH icon") {
                presentation.getIcon(false) shouldEqual RequestMapperIcons.SEARCH
            }
        }
        on("getPresentation on method PsiElement") {
            val psiFile = mock<PsiFile> {
                on { name } doReturn "Controller"
            }
            val psiElement = mock<PsiMethod> {
                on { containingFile } doReturn psiFile
                on { name } doReturn "method"
            }
            val item = RequestMappingItem(psiElement, "/api/v1/users", "POST")
            val presentation = item.presentation
            it("should return presentation with presentableText equal to the method with the path") {
                presentation.presentableText shouldEqual "POST /api/v1/users"
            }
            it("should return presentation with getLocationString equal to the method with the path") {
                presentation.locationString shouldEqual "Controller.method"
            }
            it("should return presentation with the RequestMapperIcons.SEARCH icon") {
                presentation.getIcon(false) shouldEqual RequestMapperIcons.SEARCH
            }
        }
        on("getPresentation on file PsiElement") {
            val psiElement = mock<PsiFile> {
                on { name } doReturn "Controller"
            }
            val item = RequestMappingItem(psiElement, "/api/v1/users", "POST")
            val presentation = item.presentation
            it("should return presentation with presentableText equal to the method with the path") {
                presentation.presentableText shouldEqual "POST /api/v1/users"
            }
            it("should return presentation with getLocationString equal unknownLocation") {
                presentation.locationString shouldEqual "unknownLocation"
            }
            it("should return presentation with the RequestMapperIcons.SEARCH icon") {
                presentation.getIcon(false) shouldEqual RequestMapperIcons.SEARCH
            }
        }
    }
})
