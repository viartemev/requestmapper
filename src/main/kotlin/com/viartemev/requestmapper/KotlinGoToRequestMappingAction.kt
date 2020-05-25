package com.viartemev.requestmapper

import com.intellij.openapi.project.Project

class KotlinGoToRequestMappingAction : GoToRequestMappingAction() {
    override fun getRequestMappingModel(project: Project): RequestMappingModel {
        return RequestMappingModel(project, listOf(RequestMappingByNameContributor(listOf(
            JavaAnnotationSearcher::search,
            KotlinAnnotationSearcher::search
        ))))
    }
}
