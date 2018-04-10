package com.viartemev.requestmapper.annotations

import com.viartemev.requestmapper.RequestMappingItem

object UnknownAnnotation : MappingAnnotation {
    private val mappingItems = emptyList<RequestMappingItem>()
    override fun values(): List<RequestMappingItem> = mappingItems
}
