package com.viartemev.requestmapper

import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import java.util.*
import javax.swing.Icon

class RequestMappingItem(val psiElement: PsiElement, private val urlPath: String, private val requestMethod: String) : NavigationItem {

    override fun getName(): String = this.requestMethod + " " + this.urlPath

    override fun getPresentation(): ItemPresentation = RequestMappingItemPresentation()

    override fun navigate(requestFocus: Boolean) = navigationElement().ifPresent { navigation -> navigation.navigate(requestFocus) }

    override fun canNavigate(): Boolean = navigationElement().map { it.canNavigate() }.orElse(false)

    override fun canNavigateToSource(): Boolean = true

    private fun navigationElement(): Optional<Navigatable> {
        val navigationElement: PsiElement = psiElement.navigationElement
        if (navigationElement is Navigatable) {
            return Optional.of(navigationElement)
        } else {
            return Optional.empty<Navigatable>()
        }
    }

    private inner class RequestMappingItemPresentation : ItemPresentation {

        override fun getPresentableText(): String {
            return this@RequestMappingItem.requestMethod + " " + this@RequestMappingItem.urlPath
        }

        override fun getLocationString(): String {
            val psiElement = this@RequestMappingItem.psiElement
            return when (psiElement) {
                is PsiMethod -> (psiElement.containingClass?.name ?: psiElement.containingFile.name) + "." + psiElement.name
                is PsiClass -> psiElement.name ?: psiElement.containingFile.name
                else -> "Ops..no location here"
            }
        }

        override fun getIcon(b: Boolean): Icon {
            return RequestMapperIcons.SEARCH
        }
    }
}
