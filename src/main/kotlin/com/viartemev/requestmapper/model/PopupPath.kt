package com.viartemev.requestmapper.model

/**
 * format: METHOD PATH PARAMS(if presents)
 * example: GET /account/activate params=some
 */
class PopupPath(popupItem: String) : Comparable<PopupPath> {
    private val path = popupItem.split(' ')[1]
    private val method = popupItem.split(' ')[0]

    fun toPath() = Path(path)

    companion object : Comparator<PopupPath> {
        private val comparator: java.util.Comparator<PopupPath> = Comparator { o1: PopupPath, o2: PopupPath -> o1.method.compareTo(o2.method) }
            .thenComparing { o1: PopupPath, o2: PopupPath -> o1.path.length.compareTo(o2.path.length) }

        override fun compare(o1: PopupPath?, o2: PopupPath?): Int {
            return comparator.compare(o1, o2)
        }
    }

    override fun compareTo(other: PopupPath): Int {
        return compare(this, other)
    }
}
