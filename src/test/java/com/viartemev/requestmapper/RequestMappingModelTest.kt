package com.viartemev.requestmapper

import com.intellij.testFramework.PsiTestCase

class RequestMappingModelTest : PsiTestCase() {

    fun testWithUserPatternContainsInPopupItemWithoutSpaceAndBackSlash() {
        val popupItem = "GET /do"
        val userPattern = "do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternNotContainsInPopupItemWithoutSpaceAndBackSlash() {
        val popupItem = "GET /do"
        val userPattern = "do1"
        val model = RequestMappingModel(myProject)
        assertFalse(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsEqualToPopupItem() {
        val popupItem = "GET /do"
        val userPattern = "GET /do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsBackSlash() {
        val popupItem = "GET /do"
        val userPattern = "/"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternWithBackSlashIsOnlyPartOfPopupItemCase1() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "/api"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternWithBackSlashIsOnlyPartOfPopupItemCase2() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "/v3/"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternWithTwoBackSlashAndSimilarPartInside() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "/v/"
        val model = RequestMappingModel(myProject)
        assertFalse(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternWithSpaceAndBackSlashIsOnlyPartOfPopupItem() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "GET /v3/"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternWithSpaceAndBackSlashIsOnlyPartOfPopupItemAndAnotherMethod() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "POST /v3/"
        val model = RequestMappingModel(myProject)
        assertFalse(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlWithDifferentPopupItem() {
        val popupItem = "GET /api/v2/do"
        val userPattern = "localhost:8080/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertFalse(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlSimilarOfPopupItem() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "localhost:8080/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlNotSimilarInTheEndOfPopupItem() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "localhost:8080/api/v3/init"
        val model = RequestMappingModel(myProject)
        assertFalse(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlNotSimilarInMiddleOfPopupItem() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "localhost:8080/api/v2/do"
        val model = RequestMappingModel(myProject)
        assertFalse(model.matches(popupItem, userPattern))
    }


    fun testWithUserPatternIsFullUrlSimilarOfPopupItemWithSameMethod() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "GET localhost:8080/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlSimilarOfPopupItemWithDifferentMethod() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "POST localhost:8080/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlSimilarOfPopupItemWithHttpProtocol() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "http://localhost:8080/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlSimilarOfPopupItemWithHttpsProtocol() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "https://localhost:8080/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlSimilarOfPopupItemWithWwwAndHttpProtocol() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "http://www.somesite.ru/api/v3/do"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

    fun testWithUserPatternIsFullUrlSimilarOfPopupItemWithWwwAndHttpProtocolAndQueryParams() {
        val popupItem = "GET /api/v3/do"
        val userPattern = "http://www.somesite.ru/api/v3/do?param1=true"
        val model = RequestMappingModel(myProject)
        assertTrue(model.matches(popupItem, userPattern))
    }

}