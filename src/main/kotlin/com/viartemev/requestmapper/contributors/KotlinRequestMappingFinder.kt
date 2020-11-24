package com.viartemev.requestmapper.contributors

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.search.GlobalSearchScope.projectScope
import org.jetbrains.kotlin.asJava.toLightAnnotation
import org.jetbrains.kotlin.idea.stubindex.KotlinAnnotationsIndex

class KotlinRequestMappingFinder : RequestMappingByRequestMappingItemFinder() {

    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> {
        return KotlinAnnotationsIndex
            .getInstance()
            .get(annotationName, project, projectScope(project))
            .asSequence()
            .mapNotNull { it.toLightAnnotation() }
    }
}
