package com.viartemev.requestmapper.utils

fun String.unquote(): String = if (length >= 2 && first() == '"' && last() == '"') substring(1, this.length - 1) else this

fun String.inCurlyBrackets(): Boolean = length >= 2 && first() == '{' && last() == '}'

fun String.unquoteCurlyBrackets(): String = if (this.inCurlyBrackets()) this.drop(1).dropLast(1) else this

fun String.addCurlyBrackets(): String = '{' + this + '}'

fun List<String>.dropFirstEmptyStringIfExists(): List<String> = if (this.isNotEmpty() && this.first().isEmpty()) this.drop(1) else this

fun String.isNumeric(): Boolean = this.toBigDecimalOrNull() != null