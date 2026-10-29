package io.github.shivaay.shivaaytube.innertube.models.body

import io.github.shivaay.shivaaytube.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class ResolveUrlBody(
    val context: Context,
    val url: String,
)
