package io.github.shivaay.flow.innertube.models.body

import io.github.shivaay.flow.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class ResolveUrlBody(
    val context: Context,
    val url: String,
)
