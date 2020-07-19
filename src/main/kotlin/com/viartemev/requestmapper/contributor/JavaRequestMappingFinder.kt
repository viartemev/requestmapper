package com.viartemev.requestmapper.contributor

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.GlobalSearchScope

class JavaRequestMappingFinder : RequestMappingByNameFinder() {

    override fun getAnnotationSearchers(annotationName: String, project: Project): Sequence<PsiAnnotation> {
        return JavaAnnotationIndex
            .getInstance()
            .get(annotationName, project, GlobalSearchScope.projectScope(project))
            .asSequence()
    }
}
