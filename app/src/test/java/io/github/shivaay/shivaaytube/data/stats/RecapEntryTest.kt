package io.github.shivaay.shivaaytube.data.stats

import com.google.common.truth.Truth.assertThat
import io.github.shivaay.shivaaytube.data.local.PrivacyGate
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RecapEntryTest {
    private fun gate(
        deepShivaay: Boolean,
        historyPaused: Boolean,
    ) = PrivacyGate(
        deepShivaayActive = { deepShivaay },
        watchHistoryPaused = { historyPaused },
        scrobbleDuringDeepShivaay = { false },
    )

    @Test
    fun `everything is recorded when nothing is paused`() =
        runTest {
            RecapEntry.entries.forEach { assertThat(it.allowed(gate(deepShivaay = false, historyPaused = false))).isTrue() }
        }

    @Test
    fun `Deep Flow keeps searches, actions and views out`() =
        runTest {
            val gate = gate(deepShivaay = true, historyPaused = false)

            assertThat(RecapEntry.ACTIVITY.allowed(gate)).isFalse()
            assertThat(RecapEntry.VIEW.allowed(gate)).isFalse()
        }

    @Test
    fun `paused watch history keeps views out but still counts searches`() =
        runTest {
            val gate = gate(deepShivaay = false, historyPaused = true)

            assertThat(RecapEntry.VIEW.allowed(gate)).isFalse()
            assertThat(RecapEntry.ACTIVITY.allowed(gate)).isTrue()
        }

    @Test
    fun `forgetting stored data always goes through`() =
        runTest {
            assertThat(RecapEntry.FORGET.allowed(gate(deepShivaay = true, historyPaused = true))).isTrue()
        }
}
