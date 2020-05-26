package com.viartemev.requestmapper.contributor

import com.viartemev.requestmapper.KotlinAnnotationSearcher

class KotlinRequestMappingContributor : RequestMappingByNameContributor(listOf(KotlinAnnotationSearcher::search))
