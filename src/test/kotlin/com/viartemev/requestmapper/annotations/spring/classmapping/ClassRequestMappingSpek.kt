package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiLiteralExpression
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.viartemev.requestmapper.BoundType
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ClassRequestMappingSpek : Spek({
    describe("ClassRequestMapping") {
        context("extract properties") {
            it("should return one mapping using path property with default bound type") {
                val classMappingMemberValue = mock<PsiLiteralExpression> {
                    on { text } doReturn "api"
                }
                val classMappingAnnotation = mock<PsiAnnotation> {
                    on { qualifiedName } doReturn "org.springframework.web.bind.annotation.RequestMapping"
                    on { findAttributeValue("path") } doReturn classMappingMemberValue
                }
                val classRequestMapping = ClassRequestMapping(classMappingAnnotation)

                classRequestMapping.fetchBoundMappingFromClass() shouldBeEqualTo setOf(BoundType.INBOUND)
                classRequestMapping.fetchClassMapping() shouldBeEqualTo listOf("api")
            }
            it("should return one mapping using value property ") {
                val classMappingMemberValue = mock<PsiLiteralExpression> {
                    on { text } doReturn "api"
                }
                val classMappingAnnotation = mock<PsiAnnotation> {
                    on { qualifiedName } doReturn "org.springframework.web.bind.annotation.RequestMapping"
                    on { findAttributeValue("value") } doReturn classMappingMemberValue
                }
                val classRequestMapping = ClassRequestMapping(classMappingAnnotation)

                classRequestMapping.fetchBoundMappingFromClass() shouldBeEqualTo setOf(BoundType.INBOUND)
            }
        }
    }
})
