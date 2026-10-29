package io.github.shivaay.shivaaytube.ui.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.NorthWest
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.data.local.SearchHistoryItem
import io.github.shivaay.shivaaytube.data.local.SearchType
import io.github.shivaay.shivaaytube.innertube.pages.search.SearchSuggestion
import io.github.shivaay.shivaaytube.ui.components.layout.shivaayBottomContentPadding
import io.github.shivaay.shivaaytube.ui.components.shared.DeepShivaayState
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaaySuggestionRow
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaaySwitchRow
import io.github.shivaay.shivaaytube.ui.components.shared.deepShivaayStatus

/**
 * What the search screen shows before a query is submitted: the user's own history first, then
 * what YouTube suggests, on the screen's own background rather than a raised surface. When the
 * user turned it on in Settings, a Deep Shivaay switch leads the list until they start typing.
 */
@Composable
fun SearchSuggestionsPanel(
    query: String,
    history: List<SearchHistoryItem>,
    suggestions: List<SearchSuggestion>,
    onSubmit: (String) -> Unit,
    onHistorySelect: (SearchHistoryItem) -> Unit,
    onFill: (String) -> Unit,
    onDeleteHistoryItem: (SearchHistoryItem) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier,
    deepShivaay: DeepShivaayState = DeepShivaayState(),
    onDeepShivaayChange: (Boolean) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = shivaayBottomContentPadding()),
    ) {
        if (deepShivaay.showOnSearch && query.isBlank()) {
            item(key = "deepflow") {
                ShivaaySwitchRow(
                    title = stringResource(R.string.deep_shivaay_mode_title),
                    supportingText = deepShivaayStatus(deepShivaay) ?: stringResource(R.string.deep_shivaay_mode_subtitle),
                    checked = deepShivaay.active,
                    onCheckedChange = onDeepShivaayChange,
                    leadingIcon = Icons.Outlined.VisibilityOff,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.padding(horizontal = DeepShivaayHorizontalPadding, vertical = DeepShivaayVerticalPadding),
                )
            }
        }

        searchHistoryItems(
            query = query,
            history = history,
            onSelect = onHistorySelect,
            onDeleteHistoryItem = onDeleteHistoryItem,
            onClearHistory = onClearHistory,
        )

        items(suggestions, key = { "suggestion:${it.text}" }) { suggestion ->
            ShivaaySuggestionRow(
                text = suggestion.text,
                leadingIcon = Icons.Rounded.Search,
                onClick = { onSubmit(suggestion.text) },
                query = query,
                leadingImageUrl = suggestion.entity?.thumbnailUrl,
                supportingText = suggestion.entity?.description,
                trailingIcon = Icons.Rounded.NorthWest,
                trailingContentDescription = stringResource(R.string.resize_fill),
                onTrailingClick = { onFill(suggestion.text) },
            )
        }
    }
}

/** The "Recent searches" header and rows, for any search surface's lazy list. */
fun LazyListScope.searchHistoryItems(
    query: String,
    history: List<SearchHistoryItem>,
    onSelect: (SearchHistoryItem) -> Unit,
    onDeleteHistoryItem: (SearchHistoryItem) -> Unit,
    onClearHistory: () -> Unit,
) {
    if (history.isEmpty()) return
    item(key = "history:header") {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = HeaderStartPadding, end = HeaderEndPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.recent_searches),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TextButton(onClick = onClearHistory) {
                Text(stringResource(R.string.clear_search_history))
            }
        }
    }
    items(history, key = { "history:${it.id}" }) { item ->
        ShivaaySuggestionRow(
            text = item.query,
            leadingIcon = if (item.type == SearchType.VOICE) Icons.Rounded.Mic else Icons.Rounded.History,
            onClick = { onSelect(item) },
            query = query,
            supportingText = item.filters?.let { searchFilterSummary(it) },
            trailingIcon = Icons.Rounded.Close,
            trailingContentDescription = stringResource(R.string.remove),
            onTrailingClick = { onDeleteHistoryItem(item) },
        )
    }
}

private val DeepShivaayHorizontalPadding = 12.dp
private val DeepShivaayVerticalPadding = 4.dp
private val HeaderStartPadding = 16.dp
private val HeaderEndPadding = 4.dp
