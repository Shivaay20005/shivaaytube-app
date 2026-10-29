package io.github.shivaay.shivaaytube.ui.screens.settings.index

import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.ui.components.settings.SettingEntry
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsDestination

internal object AboutIndex {
    private val page = SettingsDestination.ABOUT

    private fun entry(
        key: String,
        title: Int,
        section: Int,
        summary: Int? = null,
    ) = SettingEntry(
        key = "about.$key",
        title = title,
        summary = summary,
        section = section,
        keywords = R.string.settings_keywords_about,
        destination = page,
    )

    val changelog = entry("changelog", R.string.about_changelog, R.string.section_app, R.string.whats_new_in_shivaaytube)
    val website = entry("website", R.string.about_website, R.string.section_contact, R.string.about_website_address)
    val github = entry("github", R.string.github_label, R.string.section_contact, R.string.github_subtitle)
    val reddit = entry("reddit", R.string.about_reddit, R.string.section_contact, R.string.about_reddit_subtitle)
    val creator = entry("creator", R.string.about_creator, R.string.section_contact, R.string.about_creator_name)
    val deviceInfo = entry("device_info", R.string.about_device_info, R.string.section_device)

    val all = listOf(changelog, website, github, reddit, creator, deviceInfo)
}
