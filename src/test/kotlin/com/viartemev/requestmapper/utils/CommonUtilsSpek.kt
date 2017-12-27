package com.viartemev.requestmapper.utils

import com.intellij.testFramework.UsefulTestCase.assertEmpty
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.assertFalse

object CommonUtilsSpek : Spek({

    describe("a string") {
        on("unquote empty string") {
            it("should return empty string") {
                assertEmpty("".unquote())
            }
        }
        on("unquote string without quotes") {
            val str = "string without quote"
            it("should return original string") {
                assertEquals(str.unquote(), str)
            }
        }
        on("unquote string on quoted string") {
            it("should return string without quotes") {
                assertEquals("\"string\"".unquote(), "string")
            }
        }
        on("unquote string with one quote at the start") {
            val str = "\"string"
            it("should return original string") {
                assertEquals(str.unquote(), str)
            }
        }
        on("unquote string with one quote at the end") {
            val str = "string\""
            it("should return original string") {
                assertEquals(str.unquote(), str)
            }
        }
        on("unquote string with one quote at the middle") {
            val str = "string \" another string"
            it("should return original string") {
                assertEquals(str.unquote(), str)
            }
        }
        on("unquote empty quoted string") {
            it("should return empty string") {
                assertEmpty("\"\"".unquote())
            }
        }
        on("unquote string with one quote") {
            val str = "\""
            it("should return original string") {
                assertEquals(str.unquote(), str)
            }
        }

        on("inCurlyBrackets on empty string") {
            it("should return false") {
                assertFalse("".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on string without brackets") {
            it("should return false") {
                assertFalse("string without brackets".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on string in curly brackets") {
            it("should return true") {
                assertTrue("{hello}".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on string in curly brackets in reverse order") {
            it("should return false"){
                assertFalse("}hello{".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on string with only first curly bracket") {
            it("should return false"){
                assertFalse("{hello".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on string with only last curly bracket") {
            it("should return false"){
                assertFalse("hello}".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on string in curly brackets in the middle of string") {
            it("should return false"){
                assertFalse("hello this {is} test spring".inCurlyBrackets())
            }
        }
        on("inCurlyBrackets on empty string in curly brackets") {
            it("should return true") {
                assertTrue("{}".inCurlyBrackets())
            }
        }
    }
})