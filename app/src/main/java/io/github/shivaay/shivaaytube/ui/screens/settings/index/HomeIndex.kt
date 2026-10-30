package io.github.shivaay.shivaaytube.ui.screens.settings.index

import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.ui.components.settings.SettingAvailability
import io.github.shivaay.shivaaytube.ui.components.settings.SettingEntry
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsDestination

/** Options that live directly on the settings list rather than on a page of their own. */
internal object HomeIndex {
    private val home = SettingsDestination.HOME

    val persona =
        SettingEntry(
            key = "home.persona",
            title = R.string.taste_title,
            summary = R.string.taste_summary,
            section = R.string.settings_shivaay_engine_header,
            destination = home,
        )
    val deepShivaay =
        SettingEntry(
            key = "home.deep_flow",
            title = R.string.deep_shivaay_mode_title,
            summary = R.string.deep_shivaay_mode_subtitle,
            section = R.string.settings_shivaay_engine_header,
            destination = home,
        )
    val deepShivaayDuration =
        SettingEntry(
            key = "home.deep_shivaay_duration",
            title = R.string.deep_shivaay_expire_duration_title,
            section = R.string.settings_shivaay_engine_header,
            destination = home,
        )
    val deepShivaayHistory =
        SettingEntry(
            key = "home.deep_shivaay_history",
            title = R.string.deep_shivaay_save_history_title,
            summary = R.string.deep_shivaay_save_history_subtitle,
            section = R.string.settings_shivaay_engine_header,
            destination = home,
        )
    val deepShivaayScrobble =
        SettingEntry(
            key = "home.deep_shivaay_scrobble",
            title = R.string.deep_shivaay_scrobble_title,
            summary = R.string.deep_shivaay_scrobble_subtitle,
            section = R.string.settings_shivaay_engine_header,
            destination = home,
        )
    val deepShivaayOnSearch =
        SettingEntry(
            key = "home.deep_shivaay_on_search",
            title = R.string.deep_shivaay_on_search_title,
            summary = R.string.deep_shivaay_on_search_subtitle,
            section = R.string.settings_shivaay_engine_header,
            destination = home,
        )
    val checkForUpdates =
        SettingEntry(
            key = "home.updates",
            title = R.string.check_for_updates,
            summary = R.string.check_for_updates_subtitle,
            section = R.string.settings_header_about,
            destination = home,
            availability = SettingAvailability.GithubOnly,
        )
    val support =
        SettingEntry(
            key = "home.support",
            title = R.string.settings_item_support,
            summary = R.string.settings_item_support_subtitle,
            section = R.string.settings_header_about,
            destination = home,
        )

    /** The persona card is found through Your taste's own destination entry, not listed twice. */
    val all =
        listOf(deepShivaay, deepShivaayDuration, deepShivaayHistory, deepShivaayScrobble, deepShivaayOnSearch, checkForUpdates, support)
}
