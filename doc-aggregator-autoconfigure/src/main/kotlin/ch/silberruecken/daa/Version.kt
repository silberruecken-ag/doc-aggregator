package ch.silberruecken.daa

import com.fasterxml.jackson.annotation.JsonValue

data class Version(private val value: String) {
    @JsonValue
    override fun toString() = value
}