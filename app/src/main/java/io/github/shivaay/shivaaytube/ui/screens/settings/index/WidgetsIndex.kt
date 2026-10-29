package io.github.shivaay.shivaaytube.ui.screens.settings.index

import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.ui.components.settings.SettingEntry
import io.github.shivaay.shivaaytube.ui.components.settings.SettingsDestination
import io.github.shivaay.shivaaytube.widget.core.ShivaayWidgetEntry
import io.github.shivaay.shivaaytube.widget.core.ShivaayWidgets

internal object WidgetsIndex {
    fun entryFor(widget: ShivaayWidgetEntry) =
        SettingEntry(
            key = "widgets.${widget.id}",
            title = widget.label,
            summary = widget.description,
            section = R.string.settings_widgets_add_header,
            keywords = R.string.settings_keywords_widgets,
            destination = SettingsDestination.WIDGETS,
        )

    val all = ShivaayWidgets.catalog.map(::entryFor)
}
