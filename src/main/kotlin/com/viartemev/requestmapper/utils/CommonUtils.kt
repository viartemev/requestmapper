package com.viartemev.requestmapper.utils


fun String.unquote(): String {
    return if (this.length >= 2 && this[0] == '\"' && this[this.lastIndex] == '\"') this.substring(1, this.length - 1) else this
}
