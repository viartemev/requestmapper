package com.viartemev.requestmapper.annotations

import com.viartemev.requestmapper.RequestMappingItem

object UnknownAnnotation : MappingAnnotation {
    override fun values(): List<RequestMappingItem> = emptyList()
}
