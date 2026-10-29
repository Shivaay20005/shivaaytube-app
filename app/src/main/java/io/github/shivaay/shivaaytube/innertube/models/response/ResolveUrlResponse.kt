package io.github.shivaay.shivaaytube.innertube.models.response

import io.github.shivaay.shivaaytube.innertube.models.NavigationEndpoint
import kotlinx.serialization.Serializable

@Serializable
data class ResolveUrlResponse(
    val endpoint: NavigationEndpoint? = null,
)
