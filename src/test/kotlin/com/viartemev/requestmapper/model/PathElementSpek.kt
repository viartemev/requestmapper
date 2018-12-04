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

        on("compareToSearchPattern with an empty element") {
            it("should return false") {
                PathElement("api").compareToSearchPattern(PathElement("")).shouldBeFalse()
            }
        }
        on("compareToSearchPattern with the same string") {
            it("should return true") {
                PathElement("api").compareToSearchPattern(PathElement("api")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a String type path variable and digits") {
            it("should return true") {
                PathElement("{String:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a String type path variable and a string") {
            it("should return true") {
                PathElement("{String:id}").compareToSearchPattern(PathElement("string")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with Long type path variable and digits") {
            it("should return true") {
                PathElement("{Long:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with Long type path variable and a string") {
            it("should return false") {
                PathElement("{Long:id}").compareToSearchPattern(PathElement("string")).shouldBeFalse()
            }
        }
        on("compareToSearchPattern on an element with a String type path variable and digits (inverted order)") {
            it("should return true") {
                PathElement("{String:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a String type path variable and a string (inverted order)") {
            it("should return true") {
                PathElement("{String:id}").compareToSearchPattern(PathElement("string")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a Long type path variable and digits (inverted order)") {
            it("should return true") {
                PathElement("{Long:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a Long type path variable and a string (inverted order)") {
            it("should return false") {
                PathElement("{Long:id}").compareToSearchPattern(PathElement("string")).shouldBeFalse()
            }
        }
        on("compareToSearchPattern on an element with a regex (more than one any single character) path variable and a string") {
            it("should return true") {
                PathElement("{String:id:.+}").compareToSearchPattern(PathElement("abc")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a regex (only digits) path variable and a string") {
            it("should return false") {
                PathElement("{String:id:[\\\\d]+}").compareToSearchPattern(PathElement("abc")).shouldBeFalse()
            }
        }
        on("compareToSearchPattern on an element with a regex (only digits) path variable and digits") {
            it("should return true") {
                PathElement("{String:id:[\\\\d]+}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a Integer type path variable and digits") {
            it("should return true") {
                PathElement("{Integer:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with int type path variable and digits") {
            it("should return true") {
                PathElement("{int:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a long type path variable and digits") {
            it("should return true") {
                PathElement("{long:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a double type path variable and digits") {
            it("should return true") {
                PathElement("{double:id}").compareToSearchPattern(PathElement("123.12")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a float type path variable and digits") {
            it("should return true") {
                PathElement("{float:id}").compareToSearchPattern(PathElement("123.1")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a Double type path variable and digits") {
            it("should return true") {
                PathElement("{Double:id}").compareToSearchPattern(PathElement("123.12")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a Float type path variable and digits") {
            it("should return true") {
                PathElement("{Float:id}").compareToSearchPattern(PathElement("123.1")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a BigInteger type path variable and digits") {
            it("should return true") {
                PathElement("{BigInteger:id}").compareToSearchPattern(PathElement("123")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a BigInteger type path variable and digits") {
            it("should return true") {
                PathElement("{BigDecimal:id}").compareToSearchPattern(PathElement("123.1")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a String type path variable and search path variable") {
            it("should return true") {
                PathElement("{String:sessionId}").compareToSearchPattern(PathElement("{sess}")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a String type path variable and search path variable with type") {
            it("should return true") {
                PathElement("{String:sessionId}").compareToSearchPattern(PathElement("{String:sess}")).shouldBeTrue()
            }
        }
        on("compareToSearchPattern on an element with a path and search path variable") {
            it("should return true") {
                PathElement("getsession").compareToSearchPattern(PathElement("{sess}")).shouldBeFalse()
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
