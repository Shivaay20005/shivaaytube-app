package io.github.shivaay.shivaaytube.ui.tv.navigation

import androidx.compose.runtime.Immutable
import io.github.shivaay.shivaaytube.ui.components.layout.navigation.NavigationVisibility
import io.github.shivaay.shivaaytube.ui.components.layout.navigation.ShivaayTab
import io.github.shivaay.shivaaytube.ui.components.layout.navigation.resolveDefaultShivaayTab
import io.github.shivaay.shivaaytube.ui.components.layout.navigation.visibleShivaayTabs

/** The rail's foot: Settings is a TV root destination, not a tab the user orders. */
const val TV_SETTINGS_ROUTE = "settings"

/** The rail's tabs in the user's order, and the tab the app opens on. */
@Immutable
data class TvNavigation(
    val tabs: List<ShivaayTab>,
    val startTab: ShivaayTab,
) {
    val rootRoutes: Set<String> get() = tabs.mapTo(mutableSetOf(), ShivaayTab::route) + TV_SETTINGS_ROUTE
}

/**
 * The user's tabs as TV shows them. Shorts and Explore stay off TV, and Search is always on: a phone
 * keeps it in the top bar, which TV does not have.
 */
fun tvNavigation(
    order: List<Int>,
    defaultTabId: Int,
    visibility: NavigationVisibility,
): TvNavigation {
    val tvVisibility = visibility.copy(shorts = false, search = true, categories = false)
    return TvNavigation(
        tabs = visibleShivaayTabs(order, tvVisibility),
        startTab = resolveDefaultShivaayTab(defaultTabId, order, tvVisibility),
    )
}
