package com.viartemev.requestmapper.contributors

import com.intellij.openapi.project.Project
import com.viartemev.requestmapper.RequestMappingItem

interface RequestMappingItemFinder {
    fun findItems(project: Project?): List<RequestMappingItem>
}
