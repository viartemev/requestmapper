package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.math.BigInteger

object PathElementSpek : Spek({
    describe("PathElement") {
        on("hasPathVariable with a path element in curly brackets") {
            it("should return true") {
                PathElement("{id}").isPathVariable.shouldBeTrue()
            }
        }
        on("hasPathVariable without curly brackets") {
            it("should return false") {
                PathElement("id").isPathVariable.shouldBeFalse()
            }
        }

        on("addPathVariableType on a path element without curly brackets") {
            it("should return the original element") {
                val originalPath = "id"
                PathElement(originalPath).addPathVariableType("String").value shouldEqual originalPath
            }
        }
        on("addPathVariableType on a path element with curly brackets") {
            it("should return the original element with a type") {
                PathElement("{id}").addPathVariableType("String").value shouldEqual "{String:id}"
            }
        }
        on("addPathVariableType on a path element with curly brackets with a blank type") {
            it("should return the original element with a type String") {
                PathElement("{id}").addPathVariableType("").value shouldEqual "{String:id}"
            }
        }

        on("equals with an empty element") {
            it("should return false") {
                PathElement("api") shouldNotEqual PathElement("")
            }
        }
        on("equals with the same string") {
            it("should return true") {
                PathElement("api") shouldEqual PathElement("api")
            }
        }
        on("equals on an element with a String type path variable and digits") {
            it("should return true") {
                PathElement("{String:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with a String type path variable and a string") {
            it("should return true") {
                PathElement("{String:id}") shouldEqual PathElement("string")
            }
        }
        on("equals on an element with Long type path variable and digits") {
            it("should return true") {
                PathElement("{Long:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with Long type path variable and a string") {
            it("should return false") {
                PathElement("{Long:id}") shouldNotEqual PathElement("string")
            }
        }
        on("equals on an element with a String type path variable and digits (inverted order)") {
            it("should return true") {
                PathElement("123") shouldEqual PathElement("{String:id}")
            }
        }
        on("equals on an element with a String type path variable and a string (inverted order)") {
            it("should return true") {
                PathElement("string") shouldEqual PathElement("{String:id}")
            }
        }
        on("equals on an element with a Long type path variable and digits (inverted order)") {
            it("should return true") {
                PathElement("123") shouldEqual PathElement("{Long:id}")
            }
        }
        on("equals on an element with a Long type path variable and a string (inverted order)") {
            it("should return false") {
                PathElement("string") shouldNotEqual PathElement("{Long:id}")
            }
        }
        on("equals on an element with a regex (more than one any single character) path variable and a string") {
            it("should return true") {
                PathElement("{String:id:.+}") shouldEqual PathElement("abc")
            }
        }
        on("equals on an element with a regex (only digits) path variable and a string") {
            it("should return false") {
                PathElement("{String:id:[\\\\d]+}") shouldNotEqual PathElement("abc")
            }
        }
        on("equals on an element with a regex (only digits) path variable and digits") {
            it("should return true") {
                PathElement("{String:id:[\\\\d]+}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with a Integer type path variable and digits") {
            it("should return true") {
                PathElement("{Integer:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with int type path variable and digits") {
            it("should return true") {
                PathElement("{int:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with a long type path variable and digits") {
            it("should return true") {
                PathElement("{long:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with a BigInteger type path variable and digits") {
            it("should return true") {
                PathElement("{BigInteger:id}") shouldEqual PathElement("123")
            }
        }
        on("equals on an element with null") {
            it("should return false") {
                PathElement("{BigInteger:id}") shouldNotEqual null
            }
        }
        on("equals on an element with an element different class") {
            it("should return false") {
                PathElement("{BigInteger:id}") shouldNotEqual BigInteger("1")
            }
        }
    }
})
