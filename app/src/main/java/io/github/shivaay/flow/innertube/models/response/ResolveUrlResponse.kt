package io.github.shivaay.flow.innertube.models.response

import io.github.shivaay.flow.innertube.models.NavigationEndpoint
import kotlinx.serialization.Serializable

@Serializable
data class ResolveUrlResponse(
    val endpoint: NavigationEndpoint? = null,
)
