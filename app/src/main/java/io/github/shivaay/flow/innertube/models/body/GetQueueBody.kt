package io.github.shivaay.flow.innertube.models.body

import io.github.shivaay.flow.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetQueueBody(
    val context: Context,
    val videoIds: List<String>?,
    val playlistId: String?,
)
