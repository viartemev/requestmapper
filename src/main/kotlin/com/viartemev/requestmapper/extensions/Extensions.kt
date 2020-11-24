package com.viartemev.requestmapper.extensions

import com.intellij.openapi.extensions.ExtensionPointName
import com.viartemev.requestmapper.contributors.RequestMappingByRequestMappingItemFinder

object Extensions {

    private const val EXTENSION_POINT_NAME = "com.viartemev.requestmapper.requestMappingContributor"
    private val extensionPoints = ExtensionPointName.create<RequestMappingByRequestMappingItemFinder>(EXTENSION_POINT_NAME)

    fun getContributors(): List<RequestMappingByRequestMappingItemFinder> {
        return extensionPoints.extensionList
    }
}
