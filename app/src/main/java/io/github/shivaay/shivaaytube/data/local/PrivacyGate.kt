package io.github.shivaay.shivaaytube.data.local

import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * What Deep Shivaay and paused watch history allow to be recorded. Writers ask here instead of reading
 * the preferences themselves, so every one of them follows the same rules.
 */
@Singleton
class PrivacyGate internal constructor(
    private val deepShivaayActive: suspend () -> Boolean,
    private val watchHistoryPaused: suspend () -> Boolean,
    private val scrobbleDuringDeepShivaay: suspend () -> Boolean,
) {
    @Inject
    constructor(preferences: PlayerPreferences) : this(
        deepShivaayActive = preferences::isDeepShivaayCurrentlyActive,
        watchHistoryPaused = { preferences.watchHistoryPaused.first() },
        scrobbleDuringDeepShivaay = { preferences.deepShivaayScrobble.first() },
    )

    /** Deep Shivaay is on and its timer has not run out. */
    suspend fun isDeepShivaayActive(): Boolean = deepShivaayActive()

    suspend fun isWatchHistoryPaused(): Boolean = watchHistoryPaused()

    /** Listens go to scrobbling services unless Deep Shivaay is on and the user kept them private. */
    suspend fun allowsScrobbling(): Boolean = !deepShivaayActive() || scrobbleDuringDeepShivaay()
}
