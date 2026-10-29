package io.github.shivaay.shivaaytube.innertube.models.body

import io.github.shivaay.shivaaytube.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context,
    val query: String?,
    val params: String?,
    val continuation: String? = null,
)
