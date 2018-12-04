package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PopupItemSpek : Spek({
    describe("PopupItem") {
        context("toPath on popup without params") {
            it("should return path without method") {
                PopupPath("GET /api/v1").toPath() shouldEqual Path("/api/v1")
            }
        }
        context("toPath on popup with params") {
            it("should return path without method and params") {
                PopupPath("GET /api/v1 params=something").toPath() shouldEqual Path("/api/v1")
            }
        }
    }
})
