package io.github.shivaay.shivaaytube.innertube.models.body

import io.github.shivaay.shivaaytube.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscriptBody(
    val context: Context,
    val params: String,
)
