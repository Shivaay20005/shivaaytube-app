package io.github.shivaay.flow.discord

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DiscordFossIsolationTest {
    @Test
    fun `foss classpath excludes functional Discord implementation`() {
        val forbiddenClasses = listOf(
            "io.github.shivaay.flow.discord.DiscordTokenStore",
            "io.github.shivaay.flow.discord.DiscordAuthTokens",
            "io.github.shivaay.flow.discord.DiscordPlaybackSource",
            "io.github.shivaay.flow.discord.DiscordPresenceCoordinator",
            "io.github.shivaay.flow.discord.KizzyDiscordPresenceTransport",
            "io.github.shivaay.flow.discord.KizzyGatewayProtocol",
        )

        forbiddenClasses.forEach { className ->
            assertThat(runCatching { Class.forName(className) }.isFailure).isTrue()
        }
    }

    @Test
    fun `foss runtime reports Discord unavailable`() {
        assertThat(DiscordPresenceRuntime.settingsState.value.isAvailable).isFalse()
        assertThat(DiscordPresenceRuntime.settingsState.value.isEnabled).isFalse()
        assertThat(DiscordPresenceRuntime.settingsState.value.summary)
            .isEqualTo(DiscordSettingsSummary.UNAVAILABLE)
    }
}
