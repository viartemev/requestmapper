package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object RequestedUserPathSpek : Spek({
    describe("RequestedUserPath") {
        context("toPath on user pattern with one path elements and without host") {
            it("should return original value") {
                RequestedUserPath("/api").toPath().toFullPath() shouldEqual "/api"
            }
        }
        context("toPath on user pattern with several path elements and without host") {
            it("should return original value") {
                RequestedUserPath("/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        context("toPath on user pattern with https host") {
            it("should return path from url") {
                RequestedUserPath("https://www.somepath.com/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        context("toPath on user pattern with http host") {
            it("should return path from url") {
                RequestedUserPath("http://www.somepath.com/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        context("toPath on user pattern with site with port") {
            it("should return path from url") {
                RequestedUserPath("http://localhost:8080/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        context("toPath on user pattern with short url form") {
            it("should return path from url") {
                RequestedUserPath("http://asd/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
    }
})
