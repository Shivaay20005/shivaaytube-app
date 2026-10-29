package io.github.shivaay.shivaaytube.innertube.pages

import io.github.shivaay.shivaaytube.innertube.models.YTItem

data class ArtistItemsContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)
