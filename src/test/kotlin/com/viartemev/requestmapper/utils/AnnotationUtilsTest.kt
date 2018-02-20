package com.viartemev.requestmapper.utils

import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.PsiType
import com.intellij.testFramework.LightIdeaTestCase
import com.intellij.testFramework.LightPlatformTestCase

class AnnotationUtilsTest : LightIdeaTestCase() {

    fun testIsMethodAnnotationWithoutParent() {
        val annotation = getFactory().createAnnotationFromText("@RequestMapping(\"api\")", null)
        assertFalse(annotation.isMethodAnnotation())
    }

    fun testIsMethodAnnotationWithClassParent() {
        val factory = getFactory()
        val psiClass = factory.createClass("Controller")
        //FIXME psiClass is dummy object
        val annotation = factory.createAnnotationFromText("@RequestMapping(\"api\")", psiClass)
        assertFalse(annotation.isMethodAnnotation())
    }

    fun testIsMethodAnnotationWithVariableParent() {
        val factory = getFactory()
        val variable = factory.createVariableDeclarationStatement("dummy", PsiType.INT, null)
        //FIXME variable is dummy object
        val annotation = factory.createAnnotationFromText("@RequestMapping(\"api\")", variable)
        assertFalse(annotation.isMethodAnnotation())
    }

    //FIXME failed test
    fun IsMethodAnnotationWithMethodParent() {
        val factory = getFactory()
        val method = factory.createMethodFromText("void api() {}", null)
        //FIXME method is dummy object
        val annotation = factory.createAnnotationFromText("@RequestMapping(\"api\")", method)
        assertTrue(annotation.isMethodAnnotation())
    }

    private fun getFactory(): PsiElementFactory {
        val manager = LightPlatformTestCase.getPsiManager()
        return JavaPsiFacade.getInstance(manager.project).elementFactory
    }
}