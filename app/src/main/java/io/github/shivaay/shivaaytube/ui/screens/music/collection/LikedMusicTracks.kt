package io.github.shivaay.shivaaytube.ui.screens.music.collection

import io.github.shivaay.shivaaytube.data.local.LikedVideoInfo
import io.github.shivaay.shivaaytube.data.model.toMusicTrack
import io.github.shivaay.shivaaytube.data.music.model.MusicTrack

/**
 * Liked songs in the order they were liked. A song also kept in the music favorites comes with the
 * album, artists and length saved there; any other is built from what its like recorded.
 */
internal fun likedMusicTracks(
    liked: List<LikedVideoInfo>,
    favorites: List<MusicTrack>,
): List<MusicTrack> {
    val saved = favorites.associateBy { it.videoId }
    return liked.map { saved[it.videoId] ?: it.toMusicTrack() }
}
