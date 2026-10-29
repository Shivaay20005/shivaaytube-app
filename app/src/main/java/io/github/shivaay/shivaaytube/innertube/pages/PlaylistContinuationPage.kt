package io.github.shivaay.shivaaytube.innertube.pages

import io.github.shivaay.shivaaytube.innertube.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)
