package com.goodboards.app.analyzer

import kotlinx.serialization.Serializable

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class NewsUnit(val gameID: String, var title: String, var description: String, val url: String)

