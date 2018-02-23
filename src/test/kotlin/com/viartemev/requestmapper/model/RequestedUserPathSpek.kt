package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object RequestedUserPathSpek : Spek({
    describe("RequestedUserPath") {
        on("toPath on user pattern with one path elements and without host") {
            it("should return original value") {
                RequestedUserPath("/api").toPath().toFullPath() shouldEqual "/api"
            }
        }
        on("toPath on user pattern with several path elements and without host") {
            it("should return original value") {
                RequestedUserPath("/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        on("toPath on user pattern with https host") {
            it("should return path from url") {
                RequestedUserPath("https://www.somepath.com/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        on("toPath on user pattern with http host") {
            it("should return path from url") {
                RequestedUserPath("http://www.somepath.com/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        on("toPath on user pattern with site with port") {
            it("should return path from url") {
                RequestedUserPath("http://localhost:8080/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
        on("toPath on user pattern with short url form") {
            it("should return path from url") {
                RequestedUserPath("http://asd/api/v1").toPath().toFullPath() shouldEqual "/api/v1"
            }
        }
    }
})
