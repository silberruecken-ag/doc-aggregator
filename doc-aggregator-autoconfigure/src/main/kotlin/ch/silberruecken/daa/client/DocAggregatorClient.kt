package ch.silberruecken.daa.client

import ch.silberruecken.daa.Documentation

interface DocAggregatorClient {
    fun updateDocumentation(documentation: Documentation)
}
