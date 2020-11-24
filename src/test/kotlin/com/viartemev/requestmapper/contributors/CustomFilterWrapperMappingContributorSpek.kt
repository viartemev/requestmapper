package com.viartemev.requestmapper.contributors

import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FindSymbolParameters
import com.nhaarman.mockito_kotlin.mock
import com.viartemev.requestmapper.BoundType
import com.viartemev.requestmapper.RequestMappingItem
import org.amshove.kluent.shouldBeEqualTo
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CustomFilterWrapperMappingContributorSpek : Spek({

    describe("RequestMappingContributor") {
        context("processNames on empty navigationItems list") {
            it("should return empty list") {
                val contributor = CustomFilterWrapperMappingContributor(
                    finder = object : RequestMappingItemFinder {
                        override fun findItems(project: Project?): List<RequestMappingItem> {
                            return emptyList()
                        }
                    },
                    navigationItems = emptyList()
                )
                val itemsByName = mutableListOf<NavigationItem>()
                contributor.processElementsWithName("name", itemsByName::add, FindSymbolParameters.wrap("pattern", GlobalSearchScope.EMPTY_SCOPE))
                itemsByName.size shouldBeEqualTo 0
            }
        }
        context("processNames on 2 mapping items") {
            it("should return item a particular name") {
                val psiElement = mock<PsiElement> {}
                val navigationItems = listOf(
                    RequestMappingItem(psiElement, "/api/v1/users", "GET"),
                    RequestMappingItem(psiElement, "/api/v2/users", "GET")
                )
                val contributor = CustomFilterWrapperMappingContributor(
                    finder = object : RequestMappingItemFinder {
                        override fun findItems(project: Project?): List<RequestMappingItem> {
                            return emptyList()
                        }
                    },
                    navigationItems = navigationItems
                )
                val itemsByName = mutableListOf<NavigationItem>()
                contributor.processElementsWithName("GET /api/v1/users", itemsByName::add, FindSymbolParameters.wrap("pattern", GlobalSearchScope.EMPTY_SCOPE))
                itemsByName.size shouldBeEqualTo 1
                itemsByName[0].name shouldBeEqualTo "GET /api/v1/users"
            }
        }
        context("getNames on empty navigationItems list") {
            it("should return empty list") {
                val contributor = CustomFilterWrapperMappingContributor(
                    finder = object : RequestMappingItemFinder {
                        override fun findItems(project: Project?): List<RequestMappingItem> {
                            return emptyList()
                        }
                    },
                    navigationItems = emptyList()
                )
                val names = mutableListOf<String>()
                contributor.processNames(names::add, GlobalSearchScope.EMPTY_SCOPE, null)
                names.size shouldBeEqualTo 0
            }
        }
        context("getNames with one RequestMapping annotation") {
            it("should return one name of mapping") {
                val psiElement = mock<PsiElement> {}
                val navigationItems = listOf(
                    RequestMappingItem(psiElement, "/api", "GET")
                )

                val contributor = CustomFilterWrapperMappingContributor(
                    finder = object : RequestMappingItemFinder {
                        override fun findItems(project: Project?): List<RequestMappingItem> {
                            return navigationItems
                        }
                    },
                    navigationItems = emptyList()
                )
                val names = mutableListOf<String>()
                contributor.processNames(names::add, GlobalSearchScope.EMPTY_SCOPE, null)
                names.size shouldBeEqualTo 1
                names[0] shouldBeEqualTo "GET /api"
            }
        }
        context("processNames with filter on one RequestMapping annotation") {
            it("should return one name of mapping") {
                val psiElement = mock<PsiElement> {}
                val navigationItems = listOf(
                    RequestMappingItem(psiElement, "/api/v1", "GET", setOf(BoundType.INBOUND)),
                    RequestMappingItem(psiElement, "/api/v2", "GET", setOf(BoundType.OUTBOUND))
                )

                val contributor = CustomFilterWrapperMappingContributor(
                    finder = object : RequestMappingItemFinder {
                        override fun findItems(project: Project?): List<RequestMappingItem> {
                            return navigationItems
                        }
                    },
                    navigationItems = emptyList(),
                    customFilter = { item -> item.boundType.contains(BoundType.INBOUND) }
                )
                val names = mutableListOf<String>()
                contributor.processNames(names::add, GlobalSearchScope.EMPTY_SCOPE, null)
                names.size shouldBeEqualTo 1
                names[0] shouldBeEqualTo "GET /api/v1"
            }
        }
    }
})
