package io.github.shivaay.flow.innertube.models.body

import io.github.shivaay.flow.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context,
    val query: String?,
    val params: String?,
    val continuation: String? = null,
)
