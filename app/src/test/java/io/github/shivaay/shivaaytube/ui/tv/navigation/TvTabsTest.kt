package io.github.shivaay.shivaaytube.ui.tv.navigation

import com.google.common.truth.Truth.assertThat
import io.github.shivaay.shivaaytube.ui.components.layout.navigation.NavigationVisibility
import io.github.shivaay.shivaaytube.ui.components.layout.navigation.ShivaayTab
import org.junit.Test

class TvTabsTest {
    private val allOn = NavigationVisibility(home = true, shorts = true, music = true, search = true, categories = true)

    @Test
    fun `follows the user's order and leaves Shorts and Explore off`() {
        val order = listOf(ShivaayTab.Library, ShivaayTab.Shorts, ShivaayTab.Music, ShivaayTab.Explore, ShivaayTab.Home).map(ShivaayTab::id)

        val tabs = tvNavigation(order, defaultTabId = ShivaayTab.Home.id, visibility = allOn).tabs

        assertThat(tabs.take(3)).containsExactly(ShivaayTab.Library, ShivaayTab.Music, ShivaayTab.Home).inOrder()
        assertThat(tabs).containsNoneOf(ShivaayTab.Shorts, ShivaayTab.Explore)
    }

    @Test
    fun `always shows Search, which TV has nowhere else to put`() {
        val tabs = tvNavigation(emptyList(), ShivaayTab.Home.id, NavigationVisibility(search = false)).tabs

        assertThat(tabs).contains(ShivaayTab.Search)
    }

    @Test
    fun `hides the tabs the user hid`() {
        val tabs = tvNavigation(emptyList(), ShivaayTab.Home.id, NavigationVisibility(home = false, music = false)).tabs

        assertThat(tabs).containsNoneOf(ShivaayTab.Home, ShivaayTab.Music)
    }

    @Test
    fun `opens on the user's default tab, or the first tab when TV leaves it off`() {
        assertThat(tvNavigation(emptyList(), ShivaayTab.Library.id, allOn).startTab).isEqualTo(ShivaayTab.Library)

        val fromShorts = tvNavigation(emptyList(), ShivaayTab.Shorts.id, allOn)
        assertThat(fromShorts.startTab).isEqualTo(fromShorts.tabs.first())
    }

    @Test
    fun `roots are the tabs plus Settings`() {
        val navigation = tvNavigation(emptyList(), ShivaayTab.Home.id, allOn)

        assertThat(navigation.rootRoutes).containsExactlyElementsIn(navigation.tabs.map(ShivaayTab::route) + TV_SETTINGS_ROUTE)
    }
}
