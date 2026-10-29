package io.github.shivaay.shivaaytube.ui.components.layout.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.data.local.DEFAULT_NAV_TAB_ORDER

/**
 * A root destination of the app's primary navigation.
 *
 * [id] is what `navTabOrder` and `defaultNavTabIndex` persist, so it must never be renumbered.
 */
enum class ShivaayTab(
    val id: Int,
    val route: String,
    @StringRes val labelRes: Int,
) {
    Home(0, "home", R.string.nav_home),
    Shorts(1, "shorts", R.string.nav_shorts),
    Music(2, "music", R.string.nav_music),
    Subscriptions(3, "subscriptions", R.string.nav_subs),
    Library(4, "library", R.string.nav_library),
    Search(5, "search", R.string.nav_search),
    Explore(6, "categories", R.string.nav_explore),
    ;

    companion object {
        fun fromId(id: Int): ShivaayTab? = entries.firstOrNull { it.id == id }
    }
}

@Composable
fun ShivaayTab.icon(selected: Boolean): ImageVector =
    when (this) {
        ShivaayTab.Home -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
        ShivaayTab.Shorts -> ImageVector.vectorResource(if (selected) R.drawable.ic_shorts_filled else R.drawable.ic_shorts)
        ShivaayTab.Music -> if (selected) Icons.Filled.MusicNote else Icons.Outlined.MusicNote
        ShivaayTab.Subscriptions -> if (selected) Icons.Filled.Subscriptions else Icons.Outlined.Subscriptions
        ShivaayTab.Library -> if (selected) Icons.Filled.VideoLibrary else Icons.Outlined.VideoLibrary
        ShivaayTab.Search -> if (selected) Icons.Filled.Search else Icons.Outlined.Search
        ShivaayTab.Explore -> if (selected) Icons.Filled.Explore else Icons.Outlined.Explore
    }

data class NavigationVisibility(
    val home: Boolean = true,
    val shorts: Boolean = true,
    val music: Boolean = true,
    val search: Boolean = false,
    val categories: Boolean = false,
)

/**
 * The tabs to show, in the user's order. Subscriptions and Library cannot be hidden: every root
 * destination carries Notifications and Settings in its top bar, so one must always exist.
 */
fun visibleShivaayTabs(
    order: List<Int>,
    visibility: NavigationVisibility,
): List<ShivaayTab> {
    val enabled =
        buildSet {
            if (visibility.home) add(ShivaayTab.Home)
            if (visibility.shorts) add(ShivaayTab.Shorts)
            if (visibility.music) add(ShivaayTab.Music)
            add(ShivaayTab.Subscriptions)
            add(ShivaayTab.Library)
            if (visibility.search) add(ShivaayTab.Search)
            if (visibility.categories) add(ShivaayTab.Explore)
        }
    return (order + DEFAULT_NAV_TAB_ORDER)
        .distinct()
        .mapNotNull(ShivaayTab::fromId)
        .filter(enabled::contains)
}

fun resolveDefaultShivaayTab(
    preferredId: Int,
    order: List<Int>,
    visibility: NavigationVisibility,
): ShivaayTab {
    val visible = visibleShivaayTabs(order, visibility)
    return visible.firstOrNull { it.id == preferredId } ?: visible.first()
}
