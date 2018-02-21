package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PathElementSpek : Spek({
    describe("PathElement") {
        on("hasPathVariable with path in curly brackets") {
            it("should return true") {
                PathElement("{id}").isPathVariable.shouldBeTrue()
            }
        }
        on("hasPathVariable without curly brackets") {
            it("should return false") {
                PathElement("id").isPathVariable.shouldBeFalse()
            }
        }

        on("addPathVariableType on path element without curly brackets") {
            it("should return original element") {
                val originalPath = "id"
                PathElement(originalPath).addPathVariableType("String").value shouldEqual originalPath
            }
        }
        on("addPathVariableType on path element with curly brackets") {
            it("should return original element with type") {
                PathElement("{id}").addPathVariableType("String").value shouldEqual "{String:id}"
            }
        }
        on("addPathVariableType on path element with curly brackets with blank type") {
            it("should return original element with type String") {
                PathElement("{id}").addPathVariableType("").value shouldEqual "{String:id}"
            }
        }
    }
})