package com.viartemev.requestmapper.model

import java.net.URL

/**
 * Possible userPattern formats:
 * - http://localhost:8080/api/v1
 * - https://www.somepath.com/api/v1
 * - /api/v1
 * - api
 */
class RequestedUserPath(userPattern: String) {
    private val path: String = if (userPattern.startsWith("http") || userPattern.startsWith("https")) URL(userPattern).path else userPattern

    fun toPath() = Path(path)
}
