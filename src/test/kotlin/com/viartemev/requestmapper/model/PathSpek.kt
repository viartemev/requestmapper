package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.model.Path.Companion.isSubPathOf
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

        on("isSubPathOf 1") {
            it("1") {
                isSubPathOf(Path("/api"), (Path("/api"))).shouldBeTrue()
            }
        }
        on("isSubPathOf 2") {
            it("2") {
                isSubPathOf(Path("/api"), (Path("/api1"))).shouldBeFalse()
            }
        }
        on("isSubPathOf 3") {
            it("3") {
                isSubPathOf(Path("/api/v1"), Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSubPathOf 4") {
            it("4") {
                isSubPathOf(Path("/api/v1"), Path("/api/v2")).shouldBeFalse()
            }
        }
        on("isSubPathOf 5") {
            it("5") {
                isSubPathOf(Path("/api/v1/items"), Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSubPathOf 6") {
            it("6") {
                isSubPathOf(Path("/api/v1/items"), Path("/api/v1/items")).shouldBeTrue()
            }
        }
        on("isSubPathOf 7") {
            it("7") {
                isSubPathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 8") {
            it("8") {
                isSubPathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/asdasd")).shouldBeFalse()
            }
        }
        on("isSubPathOf 9") {
            it("9") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/asdasd")).shouldBeTrue()
            }
        }
        on("isSubPathOf 10") {
            it("10") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 11") {
            it("11") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/product"), Path("/api/v1/items/123/product")).shouldBeTrue()
            }
        }
        on("isSubPathOf 12") {
            it("12") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 13") {
            it("13") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/asdasd")).shouldBeFalse()
            }
        }
        on("isSubPathOf 14") {
            it("14") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 15") {
            it("15") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/asdasd")).shouldBeFalse()
            }
        }
        on("isSubPathOf 16") {
            it("16") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/asdaasd/123")).shouldBeFalse()
            }
        }
        on("isSubPathOf 17") {
            it("17") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/asdaasd/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 18") {
            it("18") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/123")).shouldBeFalse()
            }
        }
        on("isSubPathOf 19") {
            it("19") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 20") {
            it("20") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/products/123")).shouldBeTrue()
            }
        }
        on("isSubPathOf 21") {
            it("21") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products")).shouldBeTrue()
            }
        }
        on("isSubPathOf 22") {
            it("22") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products/1231")).shouldBeTrue()
            }
        }
        on("isSubPathOf 23") {
            it("23") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/123/price")).shouldBeTrue()
            }
        }
        on("isSubPathOf 24") {
            it("24") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("itemId/123/price")).shouldBeTrue()
            }
        }
        on("isSubPathOf 25") {
            it("25") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("localhost:8080/api/v1")).shouldBeTrue()
            }
        }
        on("isSubPathOf 26") {
            it("26") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/api/")).shouldBeTrue()
            }
        }
        on("isSubPathOf 27") {
            it("27") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/ap")).shouldBeTrue()
            }
        }
        on("isSubPathOf 28") {
            it("28") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/price/")).shouldBeFalse()
            }
        }
        on("isSubPathOf 29") {
            it("29") {
                isSubPathOf(Path("/api/v1/items/{String:itemId}/{SpecialType:productId}/price"), Path("/api/v1/items/itemId/special-type")).shouldBeTrue()
            }
        }
        on("isSubPathOf 30") {
            it("30") {
                isSubPathOf(Path("/{String:item}"), Path("/ap")).shouldBeTrue()
            }
        }
        on("isSubPathOf 31") {
            it("31") {
                isSubPathOf(Path("/{String:item}/{String:product}"), Path("/item/product")).shouldBeTrue()
            }
        }
    }
})
