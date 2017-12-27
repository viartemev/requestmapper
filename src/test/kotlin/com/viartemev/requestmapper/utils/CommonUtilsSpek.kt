package com.viartemev.requestmapper.utils

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CommonUtilsSpek : Spek({

    describe("a string") {
        on("unquote empty string") {
            it("should return empty string") {
                "".unquote().shouldBeEmpty()
            }
        }
        on("unquote string without quotes") {
            val str = "string without quote"
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        on("unquote string on quoted string") {
            it("should return string without quotes") {
                "\"string\"".unquote() shouldBeEqualTo "string"
            }
        }
        on("unquote string with one quote at the start") {
            val str = "\"string"
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        on("unquote string with one quote at the end") {
            val str = "string\""
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        on("unquote string with one quote at the middle") {
            val str = "string \" another string"
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        on("unquote empty quoted string") {
            it("should return empty string") {
                "\"\"".unquote().shouldBeEmpty()
            }
        }
        on("unquote string with one quote") {
            val str = "\""
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }

        on("inCurlyBrackets on empty string") {
            it("should return false") {
                "".inCurlyBrackets().shouldBeFalse()
            }
        }
        on("inCurlyBrackets on string without brackets") {
            it("should return false") {
                "string without brackets".inCurlyBrackets().shouldBeFalse()
            }
        }
        on("inCurlyBrackets on string in curly brackets") {
            it("should return true") {
                "{hello}".inCurlyBrackets().shouldBeTrue()
            }
        }
        on("inCurlyBrackets on string in curly brackets in reverse order") {
            it("should return false") {
                "}hello{".inCurlyBrackets().shouldBeFalse()
            }
        }
        on("inCurlyBrackets on string with only first curly bracket") {
            it("should return false") {
                "{hello".inCurlyBrackets().shouldBeFalse()
            }
        }
        on("inCurlyBrackets on string with only last curly bracket") {
            it("should return false") {
                "hello}".inCurlyBrackets().shouldBeFalse()
            }
        }
        on("inCurlyBrackets on string in curly brackets in the middle of string") {
            it("should return false") {
                "hello this {is} test spring".inCurlyBrackets().shouldBeFalse()
            }
        }
        on("inCurlyBrackets on empty string in curly brackets") {
            it("should return true") {
                "{}".inCurlyBrackets().shouldBeTrue()
            }
        }
    }
})