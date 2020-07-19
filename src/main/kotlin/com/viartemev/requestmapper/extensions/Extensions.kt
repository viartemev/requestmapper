package com.viartemev.requestmapper.extensions

import com.intellij.openapi.extensions.ExtensionPointName
import com.viartemev.requestmapper.contributor.RequestMappingByNameFinder

object Extensions {

    private const val EXTENSION_POINT_NAME = "com.viartemev.requestmapper.requestMappingContributor"
    private val extensionPoints = ExtensionPointName.create<RequestMappingByNameFinder>(EXTENSION_POINT_NAME)

    fun getContributors(): List<RequestMappingByNameFinder> {
        return extensionPoints.extensionList
    }
}
