package com.viartemev.requestmapper.utils


fun String.unquote(): String = if (length >= 2 && first() == '\"' && last() == '\"') substring(1, this.length - 1) else this

fun String.isCurlyBrackets(): Boolean = first() == '{' && last() == '}'