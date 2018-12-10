package com.viartemev.requestmapper

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.GlobalSearchScope.projectScope

object JavaAnnotationSearcher {

    fun search(annotationName: String, project: Project): Sequence<PsiAnnotation> = JavaAnnotationIndex
        .getInstance()
        .get(annotationName, project, projectScope(project))
        .asSequence()
}
