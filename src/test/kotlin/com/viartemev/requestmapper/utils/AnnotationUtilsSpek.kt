package com.viartemev.requestmapper.utils

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object AnnotationUtilsSpek : Spek({
    describe("AnnotationUtils") {
        on("isMethodAnnotation on annotation without class and method") {
            it("should return false") {
                val annotation = mock<PsiAnnotation> {}
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        on("isMethodAnnotation on class annotation") {
            it("should return false") {
                val clazz = mock<PsiClass> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn clazz
                }
                annotation.isMethodAnnotation().shouldBeFalse()
            }
        }
        on("isMethodAnnotation on method annotation") {
            it("should return false") {
                val method = mock<PsiMethod> {}
                val annotation = mock<PsiAnnotation> {
                    on { parent } doReturn method
                }
                annotation.isMethodAnnotation().shouldBeTrue()
            }
        }
    }
})
