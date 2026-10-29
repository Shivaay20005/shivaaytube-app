package io.github.shivaay.shivaaytube.ui.components.layout.navigation

import io.github.shivaay.shivaaytube.data.local.DEFAULT_NAV_TAB_ORDER
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ShivaayTabTest {
    @Test
    fun hiddenHomeFallsBackToFirstVisibleDestination() {
        val visibility = NavigationVisibility(home = false, shorts = false, music = false)

        val resolved =
            resolveDefaultShivaayTab(
                preferredId = ShivaayTab.Home.id,
                order = listOf(0, 4, 3, 1, 2, 5, 6),
                visibility = visibility,
            )

        assertEquals(ShivaayTab.Library, resolved)
        assertFalse(visibleShivaayTabs(listOf(0, 4, 3), visibility).contains(ShivaayTab.Home))
    }

    @Test
    fun reEnabledHomeRestoresAHomeDefault() {
        val resolved =
            resolveDefaultShivaayTab(
                preferredId = ShivaayTab.Home.id,
                order = listOf(3, 0, 4),
                visibility = NavigationVisibility(home = true),
            )

        assertEquals(ShivaayTab.Home, resolved)
    }

    @Test
    fun tabsFollowTheSavedOrder() {
        val tabs =
            visibleShivaayTabs(
                order = listOf(4, 3, 2, 1, 0),
                visibility = NavigationVisibility(),
            )

        assertEquals(
            listOf(ShivaayTab.Library, ShivaayTab.Subscriptions, ShivaayTab.Music, ShivaayTab.Shorts, ShivaayTab.Home),
            tabs,
        )
    }

    @Test
    fun aSavedOrderMissingNewerTabsAppendsThemInDefaultOrder() {
        val tabs =
            visibleShivaayTabs(
                order = listOf(3, 0),
                visibility = NavigationVisibility(search = true, categories = true),
            )

        assertEquals(
            listOf(
                ShivaayTab.Subscriptions,
                ShivaayTab.Home,
                ShivaayTab.Shorts,
                ShivaayTab.Music,
                ShivaayTab.Library,
                ShivaayTab.Search,
                ShivaayTab.Explore,
            ),
            tabs,
        )
    }

    @Test
    fun unknownPersistedIdsAreIgnored() {
        val tabs = visibleShivaayTabs(order = listOf(42, 4, -1, 3), visibility = NavigationVisibility())

        assertEquals(ShivaayTab.Library, tabs.first())
        assertEquals(tabs.distinct(), tabs)
    }

    @Test
    fun persistedIdsNeverChange() {
        assertEquals(DEFAULT_NAV_TAB_ORDER, ShivaayTab.entries.map(ShivaayTab::id))
        ShivaayTab.entries.forEach { tab -> assertEquals(tab, ShivaayTab.fromId(tab.id)) }
    }

    /**
     * Settings and Notifications live in the top bar of every root destination, which only works if
     * a root destination always exists. Subscriptions and Library are unconditional in
     * [visibleShivaayTabs]; this pins that down so hiding tabs can never orphan those screens.
     */
    @Test
    fun everyVisibilityCombinationKeepsAnUnhideableRootDestination() {
        val orders =
            listOf(
                DEFAULT_NAV_TAB_ORDER,
                listOf(6, 5, 4, 3, 2, 1, 0),
                listOf(4, 3),
                emptyList(),
            )

        for (bits in 0 until 32) {
            val visibility =
                NavigationVisibility(
                    home = bits and 1 != 0,
                    shorts = bits and 2 != 0,
                    music = bits and 4 != 0,
                    search = bits and 8 != 0,
                    categories = bits and 16 != 0,
                )

            for (order in orders) {
                val visible = visibleShivaayTabs(order, visibility)
                assertTrue(
                    "no unhideable root destination for $visibility / $order",
                    visible.contains(ShivaayTab.Subscriptions) || visible.contains(ShivaayTab.Library),
                )

                val resolved = resolveDefaultShivaayTab(ShivaayTab.Home.id, order, visibility)
                assertTrue(
                    "resolved default $resolved is not visible for $visibility / $order",
                    visible.contains(resolved),
                )
            }
        }
    }
}
