package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.model.Path.Companion.isSubpathOf
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

        on("isSubpathOf 1") {
            it("1") {
                isSubpathOf(Path("/api"), (Path("/api"))).shouldBeTrue()
            }
        }
        on("isSubpathOf 2") {
            it("2") {
                isSubpathOf(Path("/api"), (Path("/api1"))).shouldBeFalse()
            }
        }
        on("isSubpathOf 3") {
            it("3") {
                isSubpathOf(Path("/api/v1"), Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSubpathOf 4") {
            it("4") {
                isSubpathOf(Path("/api/v1"), Path("/api/v2")).shouldBeFalse()
            }
        }
        on("isSubpathOf 5") {
            it("5") {
                isSubpathOf(Path("/api/v1/items"), Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSubpathOf 6") {
            it("6") {
                isSubpathOf(Path("/api/v1/items"), Path("/api/v1/items")).shouldBeTrue()
            }
        }
        on("isSubpathOf 7") {
            it("7") {
                isSubpathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 8") {
            it("8") {
                isSubpathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/asdasd")).shouldBeFalse()
            }
        }
        on("isSubpathOf 9") {
            it("9") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/asdasd")).shouldBeTrue()
            }
        }
        on("isSubpathOf 10") {
            it("10") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 11") {
            it("11") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product"), Path("/api/v1/items/123/product")).shouldBeTrue()
            }
        }
        on("isSubpathOf 12") {
            it("12") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 13") {
            it("13") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/asdasd")).shouldBeFalse()
            }
        }
        on("isSubpathOf 14") {
            it("14") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 15") {
            it("15") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/asdasd")).shouldBeFalse()
            }
        }
        on("isSubpathOf 16") {
            it("16") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/asdaasd/123")).shouldBeFalse()
            }
        }
        on("isSubpathOf 17") {
            it("17") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/asdaasd/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 18") {
            it("18") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/123")).shouldBeFalse()
            }
        }
        on("isSubpathOf 19") {
            it("19") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 20") {
            it("20") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/products/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf 21") {
            it("21") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products")).shouldBeTrue()
            }
        }
        on("isSubpathOf 22") {
            it("22") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products/1231")).shouldBeTrue()
            }
        }
        on("isSubpathOf 23") {
            it("23") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/123/price")).shouldBeTrue()
            }
        }
        on("isSubpathOf 24") {
            it("24") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("itemId/123/price")).shouldBeTrue()
            }
        }
        on("isSubpathOf 25") {
            it("25") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("localhost:8080/api/v1")).shouldBeTrue()
            }
        }
        on("isSubpathOf 26") {
            it("26") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/api/")).shouldBeTrue()
            }
        }
        on("isSubpathOf 27") {
            it("27") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/ap")).shouldBeTrue()
            }
        }
        on("isSubpathOf 28") {
            it("28") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/price/")).shouldBeFalse()
            }
        }
        on("isSubpathOf 29") {
            it("29") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{SpecialType:productId}/price"), Path("/api/v1/items/itemId/special-type")).shouldBeTrue()
            }
        }
        on("isSubpathOf 30") {
            it("30") {
                isSubpathOf(Path("/{String:item}"), Path("/ap")).shouldBeTrue()
            }
        }
        on("isSubpathOf 31") {
            it("31") {
                isSubpathOf(Path("/{String:item}/{String:product}"), Path("/item/product")).shouldBeTrue()
            }
        }
    }
})
