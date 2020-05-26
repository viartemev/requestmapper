package com.viartemev.requestmapper.contributor

import com.viartemev.requestmapper.JavaAnnotationSearcher

class JavaRequestMappingContributor : RequestMappingByNameContributor(listOf(JavaAnnotationSearcher::search))
