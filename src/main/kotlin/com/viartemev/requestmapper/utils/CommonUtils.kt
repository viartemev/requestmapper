package com.viartemev.requestmapper.utils


fun String.unquote(): String {
    return if (this.length >= 2 && this[0] == '\"' && this[this.length - 1] == '\"') this.substring(1, this.length - 1) else this
}

fun isSimilar(patternList: List<String>, itemList: List<String>): Boolean {
    if (itemList.size < patternList.size) {
        return false
    }
    var i = 0
    while (i < patternList.size && i < itemList.size) {
        val pattern = patternList[i]
        val item = itemList[i]
        if (!item.startsWith("{") || !item.endsWith("}")) {
            if (pattern != item) {
                return false
            }
        }
        i++
    }
    return true
}

fun contains(patternList: List<String>, patternIdx: Int, itemList: List<String>, itemIdx: Int): Boolean {
    if (itemIdx >= itemList.size || patternIdx >= patternList.size) {
        return false
    }
    val currentItem = itemList[itemIdx]
    if (patternIdx < patternList.size) {
        val restPattern = patternList.size - patternIdx
        val restItem = itemList.size - itemIdx
        if (restItem < restPattern) {
            return false
        }
        val currentPattern = patternList[patternIdx]
        if (currentItem.contains(currentPattern)) {
            if (patternIdx == patternList.size - 1) {
                return true
            }
            return contains(patternList, patternIdx + 1, itemList, itemIdx + 1)
        }
        return contains(patternList, patternIdx, itemList, itemIdx + 1)
    }

    return false
}

