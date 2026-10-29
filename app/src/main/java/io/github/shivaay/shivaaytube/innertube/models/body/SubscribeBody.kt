package io.github.shivaay.shivaaytube.innertube.models.body

import io.github.shivaay.shivaaytube.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SubscribeBody(
    val channelIds: List<String>,
    val context: Context,
)
