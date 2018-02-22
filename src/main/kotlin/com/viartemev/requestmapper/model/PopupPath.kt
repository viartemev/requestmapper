package com.viartemev.requestmapper.model

/**
 * format: METHOD PATH PARAMS(if presents)
 * example: GET /account/activate params=some
 */
class PopupPath(popupItem: String) {
    private val path = popupItem.split(' ')[1]

    fun toPath() = Path(path)
}
