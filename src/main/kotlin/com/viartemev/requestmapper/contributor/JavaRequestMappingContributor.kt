package com.viartemev.requestmapper.contributor

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.GlobalSearchScope

class JavaRequestMappingContributor : RequestMappingByNameContributor() {
    override fun getAnnotationSearchers(): (string: String, project: Project) -> Sequence<PsiAnnotation> {
        return { annotationName, project ->
            JavaAnnotationIndex
                .getInstance()
                .get(annotationName, project, GlobalSearchScope.projectScope(project))
                .asSequence()
        }
    }
}
