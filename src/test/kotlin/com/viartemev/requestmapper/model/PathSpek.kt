package com.viartemev.requestmapper.model

import com.viartemev.requestmapper.model.Path.Companion.isSubpathOf
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PathSpek : Spek({
    describe("Path") {
        context("toFullPath on instance which contains empty string") {
            it("should return empty string") {
                Path("").toFullPath() shouldBeEqualTo ""
            }
        }
        context("toFullPath on instance which contains string without slashes") {
            it("should return the original string") {
                val path = "string"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }
        context("toFullPath on instance which contains string with slashes") {
            it("should return the original string") {
                val path = "/api/id"
                Path(path).toFullPath() shouldBeEqualTo path
            }
        }

        context("addPathVariablesTypes on instance which contains empty string") {
            it("should return the original string") {
                val path = ""
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        context("addPathVariablesTypes on instance which contains string without slashes") {
            it("should return the original string") {
                val path = "api"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        context("addPathVariablesTypes on instance which contains string without curly brackets") {
            it("should return the original string") {
                val path = "/api/id"
                Path(path).addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo path
            }
        }
        context("addPathVariablesTypes on instance which contains string with curly brackets with empty parameters map") {
            it("should return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf()).toFullPath() shouldBeEqualTo "/api/{Object:id}"
            }
        }
        context("addPathVariablesTypes on instance which contains string with curly brackets with not empty parameters map") {
            it("should return the original string") {
                Path("/api/{id}").addPathVariablesTypes(mapOf("id" to "Long")).toFullPath() shouldBeEqualTo "/api/{Long:id}"
            }
        }
        context("addPathVariablesTypes on instance which contains string with regex") {
            it("should return the original string") {
                Path("/api/{id:.+}").addPathVariablesTypes(mapOf("id" to "Long")).toFullPath() shouldBeEqualTo "/api/{Long:id:.+}"
            }
        }

        context("isSubpathOf case 1") {
            it("should return true") {
                isSubpathOf(Path("/api"), (Path("/api"))).shouldBeTrue()
            }
        }
        context("isSubpathOf case 2") {
            it("should return false") {
                isSubpathOf(Path("/api"), (Path("/api1"))).shouldBeFalse()
            }
        }
        context("isSubpathOf case 3") {
            it("should return true") {
                isSubpathOf(Path("/api/v1"), Path("/api/v1")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 4") {
            it("should return false") {
                isSubpathOf(Path("/api/v1"), Path("/api/v2")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 5") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items"), Path("/api/v1")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 6") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items"), Path("/api/v1/items")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 7") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 8") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{Long:itemId}"), Path("/api/v1/items/asdasd")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 9") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/asdasd")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 10") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}"), Path("/api/v1/items/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 11") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product"), Path("/api/v1/items/123/product")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 12") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 13") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/product/{Long:productId}"), Path("/api/v1/items/123/product/asdasd")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 14") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 15") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/api/v1/items/123/asdasd")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 16") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/asdaasd/123")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 17") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/asdaasd/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 18") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/123")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 19") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}"), Path("/items/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 20") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/products/123")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 21") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 22") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/products/{Long:productId}"), Path("/123/products/1231")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 23") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/123/price")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 24") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("itemId/123/price")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 25") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("localhost:8080/api/v1")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 26") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/api/")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 27") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/ap")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 28") {
            it("should return false") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{Long:productId}/price"), Path("/price/")).shouldBeFalse()
            }
        }
        context("isSubpathOf case 29") {
            it("should return true") {
                isSubpathOf(Path("/api/v1/items/{String:itemId}/{SpecialType:productId}/price"), Path("/api/v1/items/itemId/special-type")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 30") {
            it("should return true") {
                isSubpathOf(Path("/{String:item}"), Path("/ap")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 31") {
            it("should return true") {
                isSubpathOf(Path("/{String:item}/{String:product}"), Path("/item/product")).shouldBeTrue()
            }
        }
        context("isSubpathOf case 32") {
            it("should return true") {
                isSubpathOf(Path("/api/v1"), Path("localhost:8080/routing-path/api/v1")).shouldBeTrue()
            }
        }
    }
})
