package io.github.shivaay.flow.innertube.models.body

import io.github.shivaay.flow.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscriptBody(
    val context: Context,
    val params: String,
)
