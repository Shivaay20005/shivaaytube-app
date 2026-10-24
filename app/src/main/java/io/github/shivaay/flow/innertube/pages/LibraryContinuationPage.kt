package io.github.shivaay.flow.innertube.pages

import io.github.shivaay.flow.innertube.models.YTItem

data class LibraryContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)
