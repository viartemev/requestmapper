package com.viartemev.requestmapper

import com.intellij.openapi.command.impl.DummyProject
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

object RequestMappingModelSpek : Spek({

    val requestMappingModel = RequestMappingModel(DummyProject.getInstance())

    describe("RequestMappingModel") {
        on("matches on pattern only with slash") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/"))
            }
        }
        on("matches on pattern without slash") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "product"))
            }
        }
        on("matches on pattern without slash with lower case method") {
            it("should be case insensitive") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "post"))
            }
        }
        on("matches on pattern without slash with upper case path") {
            it("should be case insensitive") {
                assertFalse(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "ITEMS"))
            }
        }
        on("matches delegates by Path.isSubpathOf") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/items"))
            }
        }
    }
})
