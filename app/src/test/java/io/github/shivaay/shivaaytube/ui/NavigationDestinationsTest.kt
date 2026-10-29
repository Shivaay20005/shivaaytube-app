package io.github.shivaay.shivaaytube.ui

import io.github.shivaay.shivaaytube.ui.components.layout.navigation.ShivaayTab
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationDestinationsTest {
    @Test
    fun tabRootsMapToTheirTab() {
        ShivaayTab.entries.filter { it != ShivaayTab.Shorts }.forEach { tab ->
            assertEquals(tab, shivaayTabForDestination(tab.route, shortsSourceArg = null))
        }
    }

    @Test
    fun onlyTheShortsFeedIsTheShortsTab() {
        assertEquals(ShivaayTab.Shorts, shivaayTabForDestination(SHORTS_ROUTE_PATTERN, shortsSourceArg = null))
        assertEquals(ShivaayTab.Shorts, shivaayTabForDestination(SHORTS_ROUTE_PATTERN, shortsSourceArg = "feed"))
        assertNull(shivaayTabForDestination(SHORTS_ROUTE_PATTERN, shortsSourceArg = "feed:dQw4w9WgXcQ"))
        assertNull(shivaayTabForDestination(SHORTS_ROUTE_PATTERN, shortsSourceArg = "saved:"))
    }

    @Test
    fun aVideoOpenedFromShortsPlaysOverTheFirstOtherTab() {
        assertEquals("music", shortsExitRoute(listOf(ShivaayTab.Shorts, ShivaayTab.Music, ShivaayTab.Library)))
        assertEquals("home", shortsExitRoute(listOf(ShivaayTab.Home, ShivaayTab.Shorts)))
        assertEquals("home", shortsExitRoute(listOf(ShivaayTab.Shorts)))
        assertEquals("home", shortsExitRoute(emptyList()))
    }

    @Test
    fun detailScreensAreNotTabRoots() {
        listOf("settings", "settings/content", "playlists", "playlist/{playlistId}", "onboarding", null).forEach { route ->
            assertNull(shivaayTabForDestination(route, shortsSourceArg = null))
        }
    }

    @Test
    fun routesFromOutsideTheGraphResolveToTheirTab() {
        assertEquals(ShivaayTab.Search, shivaayTabForRoute("search"))
        assertEquals(ShivaayTab.Music, shivaayTabForRoute("music"))
        listOf("downloads", "history", "musicRecognize", "musicPlayer/abc", "settings").forEach { route ->
            assertNull(shivaayTabForRoute(route))
        }
    }

    @Test
    fun searchIsTheOnlyTabWithoutTheBar() {
        ShivaayTab.entries.forEach { tab -> assertEquals(tab != ShivaayTab.Search, tab.showsNavigationBar()) }
        assertFalse((null as ShivaayTab?).showsNavigationBar())
        assertTrue(ShivaayTab.Home.showsNavigationBar())
    }

    @Test
    fun onlyChannelIdsAreBrowsedDirectly() {
        assertEquals("UCXuqSBlHAE6Xw-yeJA0Tunw", youtubeChannelBrowseId("UCXuqSBlHAE6Xw-yeJA0Tunw"))
        assertEquals("UCXuqSBlHAE6Xw-yeJA0Tunw", youtubeChannelBrowseId("https://m.youtube.com/channel/UCXuqSBlHAE6Xw-yeJA0Tunw?si=abc"))
        assertNull(youtubeChannelBrowseId("@LinusTechTips"))
        assertNull(youtubeChannelBrowseId("https://www.youtube.com/@LinusTechTips"))
        assertNull(youtubeChannelBrowseId("https://www.youtube.com/c/LinusTechTips"))
        assertNull(youtubeChannelBrowseId(" "))
    }

    @Test
    fun channelHandlesUseHandleUrls() {
        assertEquals("https://www.youtube.com/@shivaay", youtubeChannelUrl("@shivaay"))
        assertEquals("https://www.youtube.com/@shivaay", youtubeChannelUrl("shivaay"))
        assertEquals(
            "https://www.youtube.com/channel/UC123",
            youtubeChannelUrl("UC123"),
        )
    }

    @Test
    fun malformedHandleChannelUrlsAreRepaired() {
        assertEquals(
            "https://www.youtube.com/@shivaay",
            youtubeChannelUrl("https://youtube.com/channel/@flow"),
        )
        assertEquals(
            "https://www.youtube.com/@shivaay",
            youtubeChannelUrl("https://m.youtube.com/@flow/videos"),
        )
    }

    @Test
    fun channelRoutesEncodeCanonicalUrls() {
        assertEquals(
            "channel?url=https%3A%2F%2Fwww.youtube.com%2F%40flow",
            youtubeChannelRoute("@flow"),
        )
    }
}
