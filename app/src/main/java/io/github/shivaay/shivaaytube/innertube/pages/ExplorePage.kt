package io.github.shivaay.shivaaytube.innertube.pages

import io.github.shivaay.shivaaytube.innertube.models.AlbumItem

data class ExplorePage(
    val newReleaseAlbums: List<AlbumItem>,
    val moodAndGenres: List<MoodAndGenres.Item>,
)
