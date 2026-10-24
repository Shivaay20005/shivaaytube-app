package io.github.shivaay.flow.ui.screens.settings.index

import io.github.shivaay.flow.R
import io.github.shivaay.flow.ui.components.settings.SettingEntry
import io.github.shivaay.flow.ui.components.settings.SettingsDestination
import io.github.shivaay.flow.widget.core.FlowWidgetEntry
import io.github.shivaay.flow.widget.core.FlowWidgets

internal object WidgetsIndex {
    fun entryFor(widget: FlowWidgetEntry) =
        SettingEntry(
            key = "widgets.${widget.id}",
            title = widget.label,
            summary = widget.description,
            section = R.string.settings_widgets_add_header,
            keywords = R.string.settings_keywords_widgets,
            destination = SettingsDestination.WIDGETS,
        )

    val all = FlowWidgets.catalog.map(::entryFor)
}
