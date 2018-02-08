package com.viartemev.requestmapper

import com.intellij.openapi.command.impl.DummyProject
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertTrue

object RequestMappingModelSpek : Spek({

    val requestMappingModel = RequestMappingModel(DummyProject.getInstance())

    describe("RequestMappingModel") {
        on("TODO1") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/"))
            }
        }
        on("TODO2") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "product"))
            }
        }
        on("TODO3") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/api"))
            }
        }
        on("TODO4") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/product"))
            }
        }
        on("TODO5") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/api/v1"))
            }
        }
        on("TODO6") {
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/product/123"))
            }
        }
    }
})