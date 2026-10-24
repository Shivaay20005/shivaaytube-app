package io.github.shivaay.flow.innertube.pages

import io.github.shivaay.flow.innertube.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)
