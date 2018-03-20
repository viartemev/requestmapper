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
            it("should return empty string") {
                Path("").toFullPath() shouldBeEqualTo ""
            }
        }
        on("toFullPath on instance which contains string without slashes") {
            it("should return the original string") {
                val path = "string"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }
        on("toFullPath on instance which contains string with slashes") {
            it("should return the original string") {
                val path = "/api/id"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }

        on("addPathVariablesTypes on instance which contains empty string") {
            it("should return the original string") {
                val path = ""
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string without slashes") {
            it("should return the original string") {
                val path = "api"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string without curly brackets") {
            it("should return the original string") {
                val path = "/api/id"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        on("addPathVariablesTypes on instance which contains string with curly brackets with empty parameters map") {
            it("should return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo "/api/{String:id}"
            }
        }
        on("addPathVariablesTypes on instance which contains string with curly brackets with not empty parameters map") {
            it("should return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf("id" to "Long")).toFullPath() shouldBeEqualTo "/api/{Long:id}"
            }
        }
        on("addPathVariablesTypes on instance which contains string with regex") {
            it("should return the original string") {
                Path("/api/{id:.+}").addPathVariablesTypes(mapOf("id" to "Long")).toFullPath() shouldBeEqualTo "/api/{Long:id:.+}"
            }
        }

        on("isSubpathOf case 1") {
            it("should return true") {
                isSubpathOf(Path("/api"), (Path("/api"))).shouldBeTrue()
            }
        }
        on("isSubpathOf case 2") {
            it("should return false") {
                isSubpathOf(Path("/api"), (Path("/api1"))).shouldBeFalse()
            }
        }
        on("isSubpathOf case 3") {
            it("should return true") {
                isSubpathOf(Path("/api/v1"), Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 4") {
            it("should return false") {
                isSubpathOf(Path("/api/v1"), Path("/api/v2")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 5") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items"), Path("/api/v1")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 6") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items"), Path("/api/v1/items")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 7") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 8") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/asdasd")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 9") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/asdasd")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 10") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 11") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product"), Path("/api/v1/items/123/product")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 12") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 13") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/asdasd")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 14") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 15") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/asdasd")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 16") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/asdaasd/123")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 17") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/asdaasd/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 18") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/123")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 19") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 20") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/products/123")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 21") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 22") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products/1231")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 23") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/123/price")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 24") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("itemId/123/price")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 25") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("localhost:8080/api/v1")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 26") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/api/")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 27") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/ap")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 28") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/price/")).shouldBeFalse()
            }
        }
        on("isSubpathOf case 29") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{SpecialType:productId}/price"), Path("/api/v1/items/itemId/special-type")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 30") {
            it("should return true") {
                isSubpathOf(Path("/{String:item}"), Path("/ap")).shouldBeTrue()
            }
        }
        on("isSubpathOf case 31") {
            it("should return true") {
                isSubpathOf(Path("/{String:item}/{String:product}"), Path("/item/product")).shouldBeTrue()
            }
        }
    }
})
