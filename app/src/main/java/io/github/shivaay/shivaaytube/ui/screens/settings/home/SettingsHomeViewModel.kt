package io.github.shivaay.shivaaytube.ui.screens.settings.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.shivaay.shivaaytube.BuildConfig
import io.github.shivaay.shivaaytube.data.local.PlayerPreferences
import io.github.shivaay.shivaaytube.data.recommendation.ShivaayNeuroEngine
import io.github.shivaay.shivaaytube.data.recommendation.ShivaayPersona
import io.github.shivaay.shivaaytube.data.update.AppRelease
import io.github.shivaay.shivaaytube.data.update.UpdateRepository
import io.github.shivaay.shivaaytube.player.DeepShivaayManager
import io.github.shivaay.shivaaytube.ui.components.shared.DeepShivaayState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/** The update check the settings list runs on request; github builds only. */
sealed interface UpdateCheckState {
    data object Idle : UpdateCheckState

    data object Checking : UpdateCheckState

    data object UpToDate : UpdateCheckState

    data object Failed : UpdateCheckState

    data class Available(
        val release: AppRelease,
    ) : UpdateCheckState
}

@HiltViewModel
class SettingsHomeViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val playerPreferences: PlayerPreferences,
        private val updates: UpdateRepository,
    ) : ViewModel() {
        val deepShivaay: StateFlow<DeepShivaayState> =
            combine(
                playerPreferences.deepShivaayActive,
                playerPreferences.deepShivaayActivatedAt,
                playerPreferences.deepShivaayExpireHours,
                playerPreferences.deepShivaaySaveToHistory,
                playerPreferences.deepShivaayScrobble,
            ) { active, activatedAt, expireHours, saveToHistory, scrobble ->
                DeepShivaayState(active, activatedAt, expireHours, saveToHistory, scrobble)
            }.combine(playerPreferences.deepShivaayOnSearch) { state, onSearch ->
                state.copy(showOnSearch = onSearch)
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MS), DeepShivaayState())

        private val _persona = MutableStateFlow<ShivaayPersona?>(null)
        val persona: StateFlow<ShivaayPersona?> = _persona.asStateFlow()

        private val _updateCheck = MutableStateFlow<UpdateCheckState>(UpdateCheckState.Idle)
        val updateCheck: StateFlow<UpdateCheckState> = _updateCheck.asStateFlow()

        init {
            refreshPersona()
        }

        /** Re-reads the persona; the engine exposes no stream, so the list refreshes it when shown. */
        fun refreshPersona() {
            viewModelScope.launch {
                _persona.value = runCatching { ShivaayNeuroEngine.getPersona(ShivaayNeuroEngine.getBrainSnapshot()) }.getOrNull()
            }
        }

        fun setDeepShivaayEnabled(enabled: Boolean) {
            viewModelScope.launch { DeepShivaayManager.setEnabled(context, enabled) }
        }

        fun setDeepShivaayExpireHours(hours: Int) {
            viewModelScope.launch { playerPreferences.setDeepShivaayExpireHours(hours) }
        }

        fun setDeepShivaaySaveToHistory(enabled: Boolean) {
            viewModelScope.launch { playerPreferences.setDeepShivaaySaveToHistory(enabled) }
        }

        fun setDeepShivaayScrobble(enabled: Boolean) {
            viewModelScope.launch { playerPreferences.setDeepShivaayScrobble(enabled) }
        }

        fun setDeepShivaayOnSearch(enabled: Boolean) {
            viewModelScope.launch { playerPreferences.setDeepShivaayOnSearch(enabled) }
        }

        fun checkForUpdates() {
            if (!BuildConfig.UPDATER_ENABLED || _updateCheck.value == UpdateCheckState.Checking) return
            _updateCheck.value = UpdateCheckState.Checking
            viewModelScope.launch {
                val result =
                    runCatching { updates.fetch() }
                        .fold(
                            onSuccess = { info -> info?.let { UpdateCheckState.Available(it) } ?: UpdateCheckState.UpToDate },
                            onFailure = { UpdateCheckState.Failed },
                        )
                _updateCheck.value = result
            }
        }

        fun consumeUpdateCheck() {
            _updateCheck.update { if (it == UpdateCheckState.Checking) it else UpdateCheckState.Idle }
        }

        private companion object {
            const val STOP_TIMEOUT_MS = 5_000L
        }
    }
