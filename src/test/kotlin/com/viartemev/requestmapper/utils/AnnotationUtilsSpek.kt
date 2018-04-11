package com.viartemev.requestmapper.utils

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object AnnotationUtilsSpek : Spek({
    describe("AnnotationUtils") {
        on("isMethodAnnotation on an annotation without a class and a method") {
            it("should return false") {
                val annotation = mock<PsiAnnotation> {}
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        on("isMethodAnnotation on a class annotation") {
            it("should return false") {
                val clazz = mock<PsiClass> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn clazz
                }
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        on("isMethodAnnotation on a method annotation") {
            it("should return true") {
                val method = mock<PsiMethod> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.isMethodAnnotation().shouldBeTrue()
            }
        }
        on("isMethodAnnotation on an unknown parent") {
            it("should return false") {
                val method = mock<PsiElement> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        on("fetchAnnotatedMethod on an unknown parent") {
            it("should throw ClassCastException") {
                val method = mock<PsiElement> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                val fetchAnnotatedMethod = { annotation.fetchAnnotatedMethod() }
                fetchAnnotatedMethod shouldThrow ClassCastException::class
            }
        }
        on("fetchAnnotatedMethod on a method annotation") {
            it("should return the method") {
                val method = mock<PsiMethod> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.fetchAnnotatedMethod() shouldEqual method
            }
        }
    }
})
