package io.github.shivaay.flow.innertube.models.body

import io.github.shivaay.flow.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchSuggestionsBody(
    val context: Context,
    val input: String,
)
