package io.github.shivaay.shivaaytube.data.engagement

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.shivaay.shivaaytube.data.model.Video
import io.github.shivaay.shivaaytube.data.recommendation.ShivaayNeuroEngine
import io.github.shivaay.shivaaytube.data.recommendation.InteractionType
import io.github.shivaay.shivaaytube.data.repository.YouTubeRepository
import javax.inject.Inject

/**
 * The learning signals an engagement action feeds the recommendation engine.
 *
 * [ShivaayNeuroEngine] is still reached through a context-keyed global, so this is the one place that
 * touches it: every engagement caller injects this instead, which keeps the global access isolated
 * behind a dependency a test can replace.
 */
class VideoEngagementSignals
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val repository: YouTubeRepository,
    ) {
        suspend fun channelSubscriptionChanged(
            channelId: String,
            channelName: String,
            subscribed: Boolean,
        ) = ShivaayNeuroEngine.onChannelSubscriptionChanged(context, channelId, channelName, subscribed)

        suspend fun videoInteraction(
            video: Video,
            interactionType: InteractionType,
        ) = ShivaayNeuroEngine.onVideoInteraction(context, video, interactionType)

        /** Learns a newly subscribed channel's declared keyword tags. */
        suspend fun channelTagsLearned(channelId: String) = repository.learnChannelTags(context, channelId)
    }
