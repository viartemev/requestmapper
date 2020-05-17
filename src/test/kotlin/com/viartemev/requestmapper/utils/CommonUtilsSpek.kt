package com.viartemev.requestmapper.utils

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CommonUtilsSpek : Spek({

    describe("a string") {
        context("unquote empty string") {
            it("should return empty string") {
                "".unquote().shouldBeEmpty()
            }
        }
        context("unquote string without quotes") {
            val str = "string without quote"
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        context("unquote string on quoted string") {
            it("should return string without quotes") {
                "\"string\"".unquote() shouldBeEqualTo "string"
            }
        }
        context("unquote string with one quote at the start") {
            val str = "\"string"
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        context("unquote string with one quote at the end") {
            val str = "string\""
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        context("unquote string with one quote at the middle") {
            val str = "string \" another string"
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }
        context("unquote empty quoted string") {
            it("should return empty string") {
                "\"\"".unquote().shouldBeEmpty()
            }
        }
        context("unquote string with one quote") {
            val str = "\""
            it("should return original string") {
                str.unquote() shouldBeEqualTo str
            }
        }

        context("inCurlyBrackets on empty string") {
            it("should return false") {
                "".inCurlyBrackets().shouldBeFalse()
            }
        }
        context("inCurlyBrackets on string without brackets") {
            it("should return false") {
                "string without brackets".inCurlyBrackets().shouldBeFalse()
            }
        }
        context("inCurlyBrackets on string in curly brackets") {
            it("should return true") {
                "{hello}".inCurlyBrackets().shouldBeTrue()
            }
        }
        context("inCurlyBrackets on string in curly brackets in reverse order") {
            it("should return false") {
                "}hello{".inCurlyBrackets().shouldBeFalse()
            }
        }
        context("inCurlyBrackets on string with only first curly bracket") {
            it("should return false") {
                "{hello".inCurlyBrackets().shouldBeFalse()
            }
        }
        context("inCurlyBrackets on string with only last curly bracket") {
            it("should return false") {
                "hello}".inCurlyBrackets().shouldBeFalse()
            }
        }
        context("inCurlyBrackets on string in curly brackets in the middle of string") {
            it("should return false") {
                "hello this {is} test spring".inCurlyBrackets().shouldBeFalse()
            }
        }
        context("inCurlyBrackets on empty string in curly brackets") {
            it("should return true") {
                "{}".inCurlyBrackets().shouldBeTrue()
            }
        }

        context("addCurlyBrackets on an empty string") {
            it("should return empty string with curly brackets") {
                "".addCurlyBrackets() shouldBeEqualTo "{}"
            }
        }

        context("addCurlyBrackets on a string") {
            it("should return string with curly brackets") {
                "some".addCurlyBrackets() shouldBeEqualTo "{some}"
            }
        }
    }

    describe("list of strings") {
        context("dropFirstEmptyStringIfExists on empty list") {
            it("should return empty list") {
                listOf<String>().dropFirstEmptyStringIfExists().shouldBeEmpty()
            }
        }
        context("dropFirstEmptyStringIfExists on list without empty elements") {
            val list = listOf("1", "2", "3")
            it("should return original list") {
                list.dropFirstEmptyStringIfExists() shouldBeEqualTo list
            }
        }
        context("dropFirstEmptyStringIfExists on list with empty element in the middle of array") {
            val list = listOf("1", "", "2", "3")
            it("should return original list") {
                list.dropFirstEmptyStringIfExists() shouldBeEqualTo list
            }
        }
        context("dropFirstEmptyStringIfExists on list with first empty element") {
            val list = listOf("", "1", "2", "3")
            it("should return original list") {
                list.dropFirstEmptyStringIfExists() shouldBeEqualTo list.subList(1, list.size)
            }
        }
    }
})
