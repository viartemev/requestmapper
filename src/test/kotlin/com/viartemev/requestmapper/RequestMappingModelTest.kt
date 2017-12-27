package com.viartemev.requestmapper

import com.intellij.openapi.command.impl.DummyProject
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.*

object RequestMappingModelSpeck : Spek({

    describe("RequestMappingModel") {
        on("TODO") {
            val requestMappingModel = RequestMappingModel(DummyProject.getInstance())
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/"))
            }
        }
        on("TODO") {
            val requestMappingModel = RequestMappingModel(DummyProject.getInstance())
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "product"))
            }
        }
        on("TODO") {
            val requestMappingModel = RequestMappingModel(DummyProject.getInstance())
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/api"))
            }
        }
        on("TODO") {
            val requestMappingModel = RequestMappingModel(DummyProject.getInstance())
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/product"))
            }
        }
        on("TODO") {
            val requestMappingModel = RequestMappingModel(DummyProject.getInstance())
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/api/v1"))
            }
        }
        on("TODO") {
            val requestMappingModel = RequestMappingModel(DummyProject.getInstance())
            it("should return true") {
                assertTrue(requestMappingModel.matches("POST /api/v1/product/{product-id}/items/{item-id}", "/product/123"))
            }
        }
    }
})