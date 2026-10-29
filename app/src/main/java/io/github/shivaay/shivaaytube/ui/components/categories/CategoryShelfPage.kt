package io.github.shivaay.shivaaytube.ui.components.categories

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import io.github.shivaay.shivaaytube.data.local.HomeFeedColumns
import io.github.shivaay.shivaaytube.data.model.Video
import io.github.shivaay.shivaaytube.innertube.pages.renderer.FeedShelf
import io.github.shivaay.shivaaytube.ui.components.layout.flowBottomContentPadding
import io.github.shivaay.shivaaytube.ui.components.shared.FeedShelfActions
import io.github.shivaay.shivaaytube.ui.components.shared.FeedShelfSections

/**
 * A destination's landing page. Only shelves that can be opened get a chevron — the news
 * destination clusters its stories without publishing a "see all" for any of them.
 *
 * No post slot is passed, so the one shelf of community posts the news destination ships is left
 * out rather than rendered as a bare header.
 */
@Composable
internal fun CategoryShelfPage(
    shelves: List<FeedShelf>,
    isLoading: Boolean,
    listState: LazyGridState,
    columnPreference: HomeFeedColumns,
    onVideoClick: (Video) -> Unit,
    onShortClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit,
    onShelfOpen: (FeedShelf) -> Unit,
) {
    val actions =
        remember(onVideoClick, onShortClick, onPlaylistClick, onShelfOpen) {
            FeedShelfActions(
                onVideoClick = onVideoClick,
                onShortClick = onShortClick,
                onPlaylistClick = onPlaylistClick,
                onSectionMore = onShelfOpen,
                canOpenSection = { it.moreParams != null },
            )
        }

    FeedShelfSections(
        sections = shelves,
        isLoading = isLoading,
        listState = listState,
        columnPreference = columnPreference,
        contentPadding = PaddingValues(bottom = flowBottomContentPadding()),
        topInset = 0.dp,
        actions = actions,
        showChannelInfo = true,
    )
}
