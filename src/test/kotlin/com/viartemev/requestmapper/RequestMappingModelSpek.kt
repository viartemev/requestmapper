package com.viartemev.requestmapper

import com.intellij.openapi.command.impl.DummyProject
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object RequestMappingModelSpek : Spek({

    val requestMappingModel = RequestMappingModel(DummyProject.getInstance())

    describe("RequestMappingModel") {
        context("matches on pattern only with slash") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/"))
            }
        }
        context("matches on pattern without slash") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "product"))
            }
        }
        context("matches on pattern without slash with lower case method") {
            it("should be case insensitive") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "post"))
            }
        }
        context("matches on pattern without slash with upper case path") {
            it("should be case insensitive") {
                assertFalse(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "ITEMS"))
            }
        }
        context("matches delegates by Path.isSubpathOf") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/items"))
            }
        }
    }
})
