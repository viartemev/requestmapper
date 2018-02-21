package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldBeEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PathSpek : Spek({
    describe("Path") {
        on("toFullPath on instance which contains empty string") {
            it("should return return empty string") {
                Path("").toFullPath() shouldBeEqualTo ""
            }
        }
        on("toFullPath on instance which contains string without slashes") {
            it("should return return the original string") {
                val path = "string"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }
        on("toFullPath on instance which contains string with slashes") {
            it("should return return the original string") {
                val path = "/api/id"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }

        on("addPathVariablesTypes on instance which contains empty string") {
            it("should return return the original string") {
                val path = ""
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string without slashes") {
            it("should return return the original string") {
                val path = "api"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string without curly brackets") {
            it("should return return the original string") {
                val path = "/api/id"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string with curly brackets with empty parameters map") {
            it("should return return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo "/api/{String:id}"
            }
        }
        on("addPathVariablesTypes on instance which contains string with curly brackets with not empty parameters map") {
            it("should return return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf("id" to "Long")).toFullPath() shouldBeEqualTo "/api/{Long:id}"
            }
        }
    }
})