package ch.silberruecken.daa

import java.net.URI

/**
 * @param url Path (without host) to the documentation.
 */
data class Documentation(val type: DocumentationType, val url: URI, val version: Version?) {
    override fun toString(): String {
        return "$type: $url"
    }
}

enum class DocumentationType { API }
