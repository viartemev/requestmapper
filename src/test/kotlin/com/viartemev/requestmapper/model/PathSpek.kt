package com.viartemev.requestmapper.model

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PathSpek : Spek({
    describe("Path") {
        on("toFullPath on instance which contains empty string") {
            it("should return return empty string") {
                Path("").toFullPath() shouldBeEqualTo ""
            }
        }
        on("toFullPath on instance which contains string without slashes") {
            it("should return return the original string") {
                val path = "string"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }
        on("toFullPath on instance which contains string with slashes") {
            it("should return return the original string") {
                val path = "/api/id"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }

        on("addPathVariablesTypes on instance which contains empty string") {
            it("should return return the original string") {
                val path = ""
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string without slashes") {
            it("should return return the original string") {
                val path = "api"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string without curly brackets") {
            it("should return return the original string") {
                val path = "/api/id"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string with curly brackets with empty parameters map") {
            it("should return return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo "/api/{String:id}"
            }
        }
        on("addPathVariablesTypes on instance which contains string with curly brackets with not empty parameters map") {
            it("should return return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf("id" to "Long")).toFullPath() shouldBeEqualTo "/api/{Long:id}"
            }
        }

        on("isSimilarTo 1") {
            it("1") {
                Path("/api").isSimilarTo(Path("/api")).shouldBeTrue()
            }
        }
        on("isSimilarTo 2") {
            it("2") {
                Path("/api").isSimilarTo(Path("/api1")).shouldBeFalse()
            }
        }
        on("isSimilarTo 3") {
            it("3") {
                Path("/api/v1").isSimilarTo(Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSimilarTo 4") {
            it("4") {
                Path("/api/v1").isSimilarTo(Path("/api/v2")).shouldBeFalse()
            }
        }
        on("isSimilarTo 5") {
            it("5") {
                Path("/api/v1/items").isSimilarTo(Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSimilarTo 6") {
            it("6") {
                Path("/api/v1/items").isSimilarTo(Path("/api/v1/items")).shouldBeTrue()
            }
        }
        on("isSimilarTo 7") {
            it("7") {
                Path("/api/v1/items/{Long:itemId}").isSimilarTo(Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 8") {
            it("8") {
                Path("/api/v1/items/{Long:itemId}").isSimilarTo(Path("/api/v1/items/asdasd")).shouldBeFalse()
            }
        }
        on("isSimilarTo 9") {
            it("9") {
                Path("/api/v1/items/{String:itemId}").isSimilarTo(Path("/api/v1/items/asdasd")).shouldBeTrue()
            }
        }
        on("isSimilarTo 10") {
            it("10") {
                Path("/api/v1/items/{String:itemId}").isSimilarTo(Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 11") {
            it("11") {
                Path("/api/v1/items/{String:itemId}/product").isSimilarTo(Path("/api/v1/items/123/product")).shouldBeTrue()
            }
        }
        on("isSimilarTo 12") {
            it("12") {
                Path("/api/v1/items/{String:itemId}/product/{Long:productId}").isSimilarTo(Path("/api/v1/items/123/product/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 13") {
            it("13") {
                Path("/api/v1/items/{String:itemId}/product/{Long:productId}").isSimilarTo(Path("/api/v1/items/123/product/asdasd")).shouldBeFalse()
            }
        }
        on("isSimilarTo 14") {
            it("14") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}").isSimilarTo(Path("/api/v1/items/123/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 15") {
            it("15") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}").isSimilarTo(Path("/api/v1/items/123/asdasd")).shouldBeFalse()
            }
        }
        on("isSimilarTo 16") {
            it("16") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}").isSimilarTo(Path("/asdaasd/123")).shouldBeFalse()
            }
        }
        on("isSimilarTo 17") {
            it("17") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}").isSimilarTo(Path("/items/asdaasd/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 18") {
            it("18") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}").isSimilarTo(Path("/123")).shouldBeFalse()
            }
        }
        on("isSimilarTo 19") {
            it("19") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}").isSimilarTo(Path("/items/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 20") {
            it("20") {
                Path("/api/v1/items/{String:itemId}/products/{Long:productId}").isSimilarTo(Path("/products/123")).shouldBeTrue()
            }
        }
        on("isSimilarTo 21") {
            it("21") {
                Path("/api/v1/items/{String:itemId}/products/{Long:productId}").isSimilarTo(Path("/123/products")).shouldBeTrue()
            }
        }
        on("isSimilarTo 22") {
            it("22") {
                Path("/api/v1/items/{String:itemId}/products/{Long:productId}").isSimilarTo(Path("/123/products/1231")).shouldBeTrue()
            }
        }
        on("isSimilarTo 23") {
            it("23") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}/price").isSimilarTo(Path("/123/price")).shouldBeTrue()
            }
        }
        on("isSimilarTo 24") {
            it("24") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}/price").isSimilarTo(Path("itemId/123/price")).shouldBeTrue()
            }
        }
        on("isSimilarTo 25") {
            it("25") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}/price").isSimilarTo(Path("localhost:8080/api/v1")).shouldBeTrue()
            }
        }
        on("isSimilarTo 26") {
            it("26") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}/price").isSimilarTo(Path("/api/")).shouldBeTrue()
            }
        }
        on("isSimilarTo 27") {
            it("27") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}/price").isSimilarTo(Path("/ap")).shouldBeTrue()
            }
        }
        on("isSimilarTo 28") {
            it("28") {
                Path("/api/v1/items/{String:itemId}/{Long:productId}/price").isSimilarTo(Path("/price/")).shouldBeFalse()
            }
        }
        on("isSimilarTo 29") {
            it("29") {
                Path("/api/v1/items/{String:itemId}/{SpecialType:productId}/price").isSimilarTo(Path("/api/v1/items/itemId/special-type")).shouldBeTrue()
            }
        }
        on("isSimilarTo 30") {
            it("30") {
                Path("/{String:item}").isSimilarTo(Path("/ap")).shouldBeTrue()
            }
        }
        on("isSimilarTo 31") {
            it("31") {
                Path("/{String:item}/{String:product}").isSimilarTo(Path("/item/product")).shouldBeTrue()
            }
        }
    }
})