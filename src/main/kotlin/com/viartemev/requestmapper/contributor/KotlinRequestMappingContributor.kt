package com.viartemev.requestmapper.contributor

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.search.GlobalSearchScope.projectScope
import org.jetbrains.kotlin.asJava.toLightAnnotation
import org.jetbrains.kotlin.idea.stubindex.KotlinAnnotationsIndex

class KotlinRequestMappingContributor : RequestMappingByNameContributor() {
    override fun getAnnotationSearchers(): (string: String, project: Project) -> Sequence<PsiAnnotation> {
        return { annotationName, project ->
            KotlinAnnotationsIndex
                .getInstance()
                .get(annotationName, project, projectScope(project))
                .asSequence()
                .mapNotNull { it.toLightAnnotation() }
        }
    }
}
