package io.github.shivaay.shivaaytube.data.local

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PrivacyGateTest {
    private fun gate(
        deepShivaay: Boolean,
        historyPaused: Boolean = false,
        scrobbleDuringDeepFlow: Boolean = false,
    ) = PrivacyGate(
        deepFlowActive = { deepShivaay },
        watchHistoryPaused = { historyPaused },
        scrobbleDuringDeepFlow = { scrobbleDuringDeepFlow },
    )

    @Test
    fun `scrobbling runs while Deep Flow is off`() =
        runTest {
            assertThat(gate(deepShivaay = false).allowsScrobbling()).isTrue()
        }

    @Test
    fun `Deep Flow stops scrobbling by default`() =
        runTest {
            assertThat(gate(deepShivaay = true).allowsScrobbling()).isFalse()
        }

    @Test
    fun `Deep Flow scrobbles when the user chose to`() =
        runTest {
            assertThat(gate(deepShivaay = true, scrobbleDuringDeepFlow = true).allowsScrobbling()).isTrue()
        }

    @Test
    fun `the gate reports both settings as they are`() =
        runTest {
            val gate = gate(deepShivaay = true, historyPaused = true)

            assertThat(gate.isDeepFlowActive()).isTrue()
            assertThat(gate.isWatchHistoryPaused()).isTrue()
        }
}
