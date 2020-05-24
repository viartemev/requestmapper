package com.viartemev.requestmapper.utils

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldThrow
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object AnnotationUtilsSpek : Spek({
    describe("AnnotationUtils") {
        context("isMethodAnnotation on an annotation without a class and a method") {
            it("should return false") {
                val annotation = mock<PsiAnnotation> {}
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        context("isMethodAnnotation on a class annotation") {
            it("should return false") {
                val clazz = mock<PsiClass> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn clazz
                }
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        context("isMethodAnnotation on a method annotation") {
            it("should return true") {
                val method = mock<PsiMethod> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.isMethodAnnotation().shouldBeTrue()
            }
        }
        context("isMethodAnnotation on an unknown parent") {
            it("should return false") {
                val method = mock<PsiElement> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        context("fetchAnnotatedMethod on an unknown parent") {
            it("should throw ClassCastException") {
                val method = mock<PsiElement> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                val fetchAnnotatedMethod = { annotation.fetchAnnotatedMethod() }
                fetchAnnotatedMethod shouldThrow ClassCastException::class
            }
        }
        context("fetchAnnotatedMethod on a method annotation") {
            it("should return the method") {
                val method = mock<PsiMethod> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.fetchAnnotatedMethod() shouldBeEqualTo method
            }
        }
    }
})
