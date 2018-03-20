package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
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

        on("equals with empty element") {
            it("should return true") {
                PathElement("api") shouldEqual PathElement("api")
            }
        }
        on("equals with string") {
            it("should return true") {
                PathElement("api") shouldEqual PathElement("api")
            }
        }
        on("equals on element with String type path variable and digital") {
            it("should return true") {
                PathElement("{String:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on element with String type path variable and string") {
            it("should return true") {
                PathElement("{String:id}") shouldEqual PathElement("string")
            }
        }
        on("equals on element with Long type path variable and digital") {
            it("should return true") {
                PathElement("{Long:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on element with Long type path variable and string") {
            it("should return false") {
                PathElement("{Long:id}") shouldNotEqual PathElement("string")
            }
        }
        on("equals on element with String type path variable and digital with inverted order") {
            it("should return true") {
                PathElement("123") shouldEqual PathElement("{String:id}")
            }
        }
        on("equals on element with String type path variable and string with inverted order") {
            it("should return true") {
                PathElement("string") shouldEqual PathElement("{String:id}")
            }
        }
        on("equals on element with Long type path variable and digital with inverted order") {
            it("should return true") {
                PathElement("123") shouldEqual PathElement("{Long:id}")
            }
        }
        on("equals on element with Long type path variable and string with inverted order") {
            it("should return false") {
                PathElement("string") shouldNotEqual PathElement("{Long:id}")
            }
        }
        on("equals on element with regex (more than one any single character) path variable and string") {
            it("should return true") {
                PathElement("{String:id:.+}") shouldEqual PathElement("abc")
            }
        }
        on("equals on element with regex (only digits) path variable and string") {
            it("should return false") {
                PathElement("{String:id:[\\\\d]+}") shouldNotEqual PathElement("abc")
            }
        }
        on("equals on element with regex (only digits) path variable and digits") {
            it("should return true") {
                PathElement("{String:id:[\\\\d]+}") shouldEqual PathElement("123")
            }
        }
    }
})
