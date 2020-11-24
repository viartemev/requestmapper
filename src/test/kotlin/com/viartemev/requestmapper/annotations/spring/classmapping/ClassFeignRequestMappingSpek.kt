package com.viartemev.requestmapper.annotations.spring.classmapping

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiLiteralExpression
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.viartemev.requestmapper.BoundType
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ClassFeignRequestMappingSpek : Spek({
    describe("FeignRequestMapping") {
        context("extract properties") {
            it("should return one mapping using path and name properties with outbound type") {
                val pathMemberText = mock<PsiLiteralExpression> {
                    on { text } doReturn "/api"
                }
                val nameMemberText = mock<PsiLiteralExpression> {
                    on { text } doReturn "v2"
                }
                val classMappingAnnotation = mock<PsiAnnotation> {
                    on { qualifiedName } doReturn "org.springframework.web.bind.annotation.RequestMapping"
                    on { findAttributeValue("path") } doReturn pathMemberText
                    on { findAttributeValue("name") } doReturn nameMemberText
                }
                val classRequestMapping = ClassFeignClientMapping(classMappingAnnotation)

                classRequestMapping.fetchBoundMappingFromClass() shouldBeEqualTo setOf(BoundType.OUTBOUND)
                classRequestMapping.fetchClassMapping() shouldBeEqualTo listOf("v2/api")
            }
            it("should return one mapping using path and name properties with outbound type") {
                val pathMemberText = mock<PsiLiteralExpression> {
                    on { text } doReturn "api"
                }
                val urlMemberText = mock<PsiLiteralExpression> {
                    on { text } doReturn "v2"
                }
                val classMappingAnnotation = mock<PsiAnnotation> {
                    on { qualifiedName } doReturn "org.springframework.web.bind.annotation.RequestMapping"
                    on { findAttributeValue("path") } doReturn pathMemberText
                    on { findAttributeValue("url") } doReturn urlMemberText
                }
                val classRequestMapping = ClassFeignClientMapping(classMappingAnnotation)

                classRequestMapping.fetchBoundMappingFromClass() shouldBeEqualTo setOf(BoundType.OUTBOUND)
                classRequestMapping.fetchClassMapping() shouldBeEqualTo listOf("v2/api")
            }
        }
    }
})
