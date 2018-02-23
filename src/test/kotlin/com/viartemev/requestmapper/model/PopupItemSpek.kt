package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PopupItemSpek : Spek({
    describe("PopupItem") {
        on("toPath on popup without params") {
            it("should return path without method") {
                PopupPath("GET /api/v1").toPath() shouldEqual Path("/api/v1")
            }
        }
        on("toPath on popup with params") {
            it("should return path without method and params") {
                PopupPath("GET /api/v1 params=something").toPath() shouldEqual Path("/api/v1")
            }
        }
    }
})
