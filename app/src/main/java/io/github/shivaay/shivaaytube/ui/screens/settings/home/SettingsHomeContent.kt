package io.github.shivaay.shivaaytube.ui.screens.settings.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import io.github.shivaay.shivaaytube.BuildConfig
import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.data.recommendation.ShivaayPersona
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsDestination
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsGroupScope
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsListScope
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsTarget
import io.github.shivaay.shivaaytube.ui.components.settings.nav
import io.github.shivaay.shivaaytube.ui.components.settings.switch
import io.github.shivaay.shivaaytube.ui.components.shared.DeepShivaayState
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaayNavRow
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaaySwitchRow
import io.github.shivaay.shivaaytube.ui.components.shared.deepShivaayStatus
import io.github.shivaay.shivaaytube.ui.screens.settings.index.DestinationIndex
import io.github.shivaay.shivaaytube.ui.screens.settings.index.HomeIndex

@Immutable
internal data class SettingsHomeState(
    val selected: SettingsDestination?,
    val persona: ShivaayPersona?,
    val deepShivaay: DeepShivaayState,
    /** Shown on the update row while a check the user started is running; null when idle. */
    val updateCheckingLabel: String?,
    /** Attached to the [selected] page's row, so a host can return focus to it. */
    val selectedRowFocus: FocusRequester? = null,
)

internal class SettingsHomeActions(
    val onOpen: (SettingsTarget) -> Unit,
    val onOpenDonations: () -> Unit,
    val onDeepShivaayChange: (Boolean) -> Unit,
    val onDurationClick: () -> Unit,
    val onSaveHistoryChange: (Boolean) -> Unit,
    val onScrobbleChange: (Boolean) -> Unit,
    val onShowOnSearchChange: (Boolean) -> Unit,
    val onCheckForUpdates: () -> Unit,
)

/** The settings list when no search is running. */
internal fun SettingsListScope.homeContent(
    state: SettingsHomeState,
    actions: SettingsHomeActions,
) {
    item(HomeIndex.persona.key) {
        PersonaEntryCard(
            persona = state.persona,
            selected = state.selected == SettingsDestination.TASTE,
            onOpen = { actions.onOpen(SettingsTarget(SettingsDestination.TASTE)) },
            modifier = state.selectedFocusModifier(SettingsDestination.TASTE),
        )
    }

    fun SettingsGroupScope.page(destination: SettingsDestination) =
        nav(
            entry = DestinationIndex.entry(destination),
            onClick = { actions.onOpen(SettingsTarget(destination)) },
            icon = destinationIcon(destination),
            selected = state.selected == destination,
            showChevron = state.selected == null,
            modifier = state.selectedFocusModifier(destination),
        )

    group(key = "home.engine", header = R.string.settings_shivaay_engine_header) {
        row(HomeIndex.deepShivaay.key) { shape ->
            ShivaaySwitchRow(
                title = stringResource(HomeIndex.deepShivaay.title),
                supportingText = deepShivaayStatus(state.deepShivaay) ?: stringResource(R.string.deep_shivaay_mode_subtitle),
                checked = state.deepShivaay.active,
                onCheckedChange = actions.onDeepShivaayChange,
                leadingIcon = HomeRowIcons.DeepShivaay,
                shape = shape,
            )
        }
        row(HomeIndex.deepShivaayDuration.key) { shape ->
            ShivaayNavRow(
                title = stringResource(HomeIndex.deepShivaayDuration.title),
                supportingText = deepShivaayDurationLabel(state.deepShivaay.expireHours),
                onClick = actions.onDurationClick,
                leadingIcon = HomeRowIcons.DeepShivaayDuration,
                shape = shape,
            )
        }
        switch(
            HomeIndex.deepShivaayHistory,
            checked = state.deepShivaay.saveToHistory,
            onCheckedChange = actions.onSaveHistoryChange,
            icon = HomeRowIcons.DeepShivaayHistory,
        )
        switch(
            HomeIndex.deepShivaayScrobble,
            checked = state.deepShivaay.scrobble,
            onCheckedChange = actions.onScrobbleChange,
            icon = HomeRowIcons.DeepShivaayScrobble,
        )
        switch(
            HomeIndex.deepShivaayOnSearch,
            checked = state.deepShivaay.showOnSearch,
            onCheckedChange = actions.onShowOnSearchChange,
            icon = HomeRowIcons.DeepShivaayOnSearch,
        )
        page(SettingsDestination.TOPICS)
    }
    group(key = "home.look", header = R.string.settings_group_look) {
        page(SettingsDestination.APPEARANCE)
        page(SettingsDestination.LANGUAGE_REGION)
        page(SettingsDestination.WIDGETS)
    }
    group(key = "home.watching", header = R.string.settings_group_watching) {
        page(SettingsDestination.PLAYBACK)
        page(SettingsDestination.QUALITY)
        page(SettingsDestination.CONTENT)
        page(SettingsDestination.INTEGRATIONS)
    }
    group(key = "home.data", header = R.string.settings_group_data) {
        page(SettingsDestination.BACKUP)
        page(SettingsDestination.SYNC)
        page(SettingsDestination.HISTORY)
        page(SettingsDestination.DOWNLOADS)
    }
    group(key = "home.system", header = R.string.settings_group_system) {
        page(SettingsDestination.NOTIFICATIONS)
        page(SettingsDestination.NETWORK)
        page(SettingsDestination.WELLBEING)
    }
    group(key = "home.about", header = R.string.settings_header_about) {
        page(SettingsDestination.ABOUT)
        page(SettingsDestination.DIAGNOSTICS)
        if (BuildConfig.UPDATER_ENABLED) {
            nav(
                HomeIndex.checkForUpdates,
                onClick = actions.onCheckForUpdates,
                value = state.updateCheckingLabel,
                enabled = state.updateCheckingLabel == null,
                showChevron = false,
                icon = HomeRowIcons.Updates,
            )
        }
        nav(HomeIndex.support, onClick = actions.onOpenDonations, icon = HomeRowIcons.Support)
    }
}

private fun SettingsHomeState.selectedFocusModifier(destination: SettingsDestination): Modifier =
    selectedRowFocus?.takeIf { selected == destination }?.let { Modifier.focusRequester(it) } ?: Modifier
