package io.github.shivaay.shivaaytube.ui.screens.sync

import com.google.common.truth.Truth.assertThat
import io.github.shivaay.shivaaytube.sync.protocol.SyncCollection
import org.junit.Test

class SyncCollectionsTest {
    @Test
    fun `an Android receiver accepts everything the picker lets a sender choose`() {
        assertThat(SyncCollection.ANDROID_SYNCABLE).containsAtLeastElementsIn(COLLECTION_KEYS)
    }
}
