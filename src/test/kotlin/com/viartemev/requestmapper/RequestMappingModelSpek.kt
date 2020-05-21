package com.viartemev.requestmapper

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object RequestMappingModelSpek : Spek({

    describe("RequestMappingModel") {
        context("matches on pattern only with slash") {
            it("should return true") {
                assertTrue(RequestMappingItemProvider.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/"))
            }
        }
        context("matches on pattern without slash") {
            it("should return true") {
                assertTrue(RequestMappingItemProvider.matches("POST /api/v1/product/{product-id}/items/{item-id}", "product"))
            }
        }
        context("matches on pattern without slash with lower case method") {
            it("should be case insensitive") {
                assertTrue(RequestMappingItemProvider.matches("POST /api/v1/product/{product-id}/items/{item-id}", "post"))
            }
        }
        context("matches on pattern without slash with upper case path") {
            it("should be case insensitive") {
                assertFalse(RequestMappingItemProvider.matches("POST /api/v1/product/{product-id}/items/{item-id}", "ITEMS"))
            }
        }
        context("matches delegates by Path.isSubpathOf") {
            it("should return true") {
                assertTrue(RequestMappingItemProvider.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/items"))
            }
        }
    }
})
