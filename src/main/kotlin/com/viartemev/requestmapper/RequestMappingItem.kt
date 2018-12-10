package com.viartemev.requestmapper

import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

class RequestMappingItem(val psiElement: PsiElement, private val urlPath: String, private val requestMethod: String) : NavigationItem {

    private val navigationElement = psiElement.navigationElement as? Navigatable

    override fun getName(): String = this.requestMethod + " " + this.urlPath

    override fun getPresentation(): ItemPresentation = RequestMappingItemPresentation()

    override fun navigate(requestFocus: Boolean) = navigationElement?.navigate(requestFocus) ?: Unit

    override fun canNavigate(): Boolean = navigationElement?.canNavigate() ?: false

    override fun canNavigateToSource(): Boolean = true

    override fun toString(): String {
        return "RequestMappingItem(urlPath='$urlPath', requestMethod='$requestMethod')"
    }

    private inner class RequestMappingItemPresentation : ItemPresentation {

        override fun getPresentableText() = this@RequestMappingItem.requestMethod + " " + this@RequestMappingItem.urlPath

        override fun getLocationString(): String {
            val psiElement = this@RequestMappingItem.psiElement
            val fileName = psiElement.containingFile?.name
            return when (psiElement) {
                is PsiMethod -> (psiElement.containingClass?.name ?: fileName ?: "unknownFile") + "." + psiElement.name
                is PsiClass -> psiElement.name ?: fileName ?: "unknownFile"
                else -> "unknownLocation"
            }
        }

        override fun getIcon(b: Boolean) = RequestMapperIcons.SEARCH
    }
}
