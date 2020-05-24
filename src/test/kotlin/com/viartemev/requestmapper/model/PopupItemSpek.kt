package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PopupItemSpek : Spek({
    describe("PopupItem") {
        context("toPath on popup without params") {
            it("should return path without method") {
                PopupPath("GET /api/v1").toPath() shouldBeEqualTo Path("/api/v1")
            }
        }
        context("toPath on popup with params") {
            it("should return path without method and params") {
                PopupPath("GET /api/v1 params=something").toPath() shouldBeEqualTo Path("/api/v1")
            }
        }

        context("compare by Method") {
            it("should return sort by method") {
                val path1 = PopupPath("GET /api/v1")
                val path2 = PopupPath("POST /api/v1")
                val path3 = PopupPath("PUT /api/v1")
                val list = listOf(path3, path2, path1)
                list.sorted() shouldBeEqualTo listOf(path1, path2, path3)
            }
        }

        context("compare by length") {
            it("should return sort by length") {
                val path1 = PopupPath("GET /api/v1")
                val path2 = PopupPath("GET /api/v10")
                val path3 = PopupPath("GET /api/v100")
                val list = listOf(path3, path2, path1)
                list.sorted() shouldBeEqualTo listOf(path1, path2, path3)
            }
        }
    }
})
