package ch.silberruecken.daa

import java.net.URI

data class Documentation(val type: DocumentationType, val url: URI, val version: Version? = null) {
    override fun toString(): String {
        return "$type: $url"
    }
}

enum class DocumentationType { API }
