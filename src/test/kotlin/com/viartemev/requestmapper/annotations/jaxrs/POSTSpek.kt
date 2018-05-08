package com.viartemev.requestmapper.annotations.jaxrs

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiMethod
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object POSTSpek : Spek({
    describe("POST") {
        on("extractMethod") {
            it("should return POST") {
                val annotation = mock<PsiAnnotation> { on { parent } doReturn mock<PsiMethod> {} }
                POST(annotation).extractMethod() shouldBeEqualTo "POST"
            }
        }
    }
})
