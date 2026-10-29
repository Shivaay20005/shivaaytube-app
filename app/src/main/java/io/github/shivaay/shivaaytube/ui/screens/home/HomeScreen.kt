package io.github.shivaay.shivaaytube.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.shivaay.shivaaytube.R
import io.github.shivaay.shivaaytube.data.local.HomeFeedColumns
import io.github.shivaay.shivaaytube.data.local.HomeViewMode
import io.github.shivaay.shivaaytube.data.local.PlayerPreferences
import io.github.shivaay.shivaaytube.data.local.VideoHistoryEntry
import io.github.shivaay.shivaaytube.data.model.Video
import io.github.shivaay.shivaaytube.data.shorts.queue.ShortsQueueSource
import io.github.shivaay.shivaaytube.player.DeepShivaayManager
import io.github.shivaay.shivaaytube.ui.components.layout.LocalShivaayBottomInsets
import io.github.shivaay.shivaaytube.ui.components.layout.floatAboveBottomChrome
import io.github.shivaay.shivaaytube.ui.components.layout.topbar.ShivaayTopBar
import io.github.shivaay.shivaaytube.ui.components.rememberFeedGridLayout
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaayPullToRefreshBox
import io.github.shivaay.shivaaytube.ui.screens.home.chips.HomeChipRow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val MILLIS_PER_SECOND = 1000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onVideoClick: (Video) -> Unit,
    onShortClick: (ShortsQueueSource) -> Unit,
    onSearchClick: () -> Unit,
    onNavigateToHistory: () -> Unit = {},
    onOpenShortsFeed: () -> Unit = {},
    onPlayMix: (videos: List<Video>, title: String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val preferences = remember(context) { PlayerPreferences(context) }
    val homeViewMode by preferences.homeViewMode.collectAsStateWithLifecycle(initialValue = HomeViewMode.GRID)
    val homeFeedColumns by preferences.homeFeedColumns.collectAsStateWithLifecycle(initialValue = HomeFeedColumns.AUTO)
    val homeFeedEnabled by preferences.homeFeedEnabled.collectAsStateWithLifecycle(initialValue = true)
    val refreshHomeOnReselect by preferences.refreshHomeOnReselect.collectAsStateWithLifecycle(initialValue = true)
    val showAppLogoIcon by preferences.showAppLogoIcon.collectAsStateWithLifecycle(initialValue = true)
    val deepShivaayActive by preferences.deepShivaayActive.collectAsStateWithLifecycle(initialValue = false)

    val gridStates = rememberHomeGridStates()
    val coroutineScope = rememberCoroutineScope()
    val chips by viewModel.chips.collectAsStateWithLifecycle()
    val chipRowState = rememberTopAppBarState()
    val chipRowBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(chipRowState)
    val snackbarHostState = remember { SnackbarHostState() }
    val refreshFailed = stringResource(R.string.home_refresh_failed)
    val retryLabel = stringResource(R.string.retry)

    LaunchedEffect(viewModel) {
        viewModel.refreshFailures.collectLatest {
            val result = snackbarHostState.showSnackbar(refreshFailed, actionLabel = retryLabel, duration = SnackbarDuration.Short)
            if (result == SnackbarResult.ActionPerformed) viewModel.refreshFeed()
        }
    }

    HomeFeedEffects(
        viewModel = viewModel,
        uiState = uiState,
        chips = chips,
        gridStates = gridStates,
        homeFeedEnabled = homeFeedEnabled,
        refreshHomeOnReselect = refreshHomeOnReselect,
    )

    val haptic = LocalHapticFeedback.current
    val deepShivaayLabel = stringResource(R.string.deep_shivaay_mode_title)
    val toggleDeepShivaay: () -> Unit = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        coroutineScope.launch { DeepShivaayManager.toggle(context) }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = {
            SnackbarHost(snackbarHostState, Modifier.floatAboveBottomChrome(LocalShivaayBottomInsets.current))
        },
        topBar = {
            ShivaayTopBar(
                title = {
                    Text(
                        stringResource(R.string.app_name_uppercase),
                        modifier = Modifier.deepShivaayLongPress(deepShivaayLabel, toggleDeepShivaay),
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                            ),
                    )
                },
                leading =
                    if (showAppLogoIcon) {
                        {
                            ShivaayHeaderLogoIcon(
                                isDeepShivaayActive = deepShivaayActive,
                                modifier = Modifier.size(32.dp).deepShivaayLongPress(deepShivaayLabel, toggleDeepShivaay),
                            )
                        }
                    } else {
                        null
                    },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = stringResource(R.string.search),
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
        ) {
            if (homeFeedEnabled && chips.chips.size > 1) {
                HomeChipRow(
                    state = chips,
                    scrollState = chipRowState,
                    onSelect = { key ->
                        chipRowState.heightOffset = 0f
                        viewModel.selectChip(key)
                    },
                )
            }
            ResettableHomePullToRefreshBox(
                resetKey = uiState.isLoading && uiState.videos.isEmpty(),
                isRefreshing = if (chips.isAll) uiState.isRefreshing else chips.isRefreshing,
                onRefresh = { if (chips.isAll) viewModel.refreshFeed() else viewModel.refreshSelectedChip() },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .nestedScroll(chipRowBehavior.nestedScrollConnection),
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    HomeFeedBody(
                        viewModel = viewModel,
                        uiState = uiState,
                        chips = chips,
                        gridStates = gridStates,
                        feedLayout = rememberFeedGridLayout(maxWidth, homeFeedColumns),
                        isListView = homeViewMode == HomeViewMode.LIST,
                        homeFeedEnabled = homeFeedEnabled,
                        onVideoClick = onVideoClick,
                        onPlayMix = onPlayMix,
                        onShortClick = onShortClick,
                        onNavigateToHistory = onNavigateToHistory,
                        onOpenShortsFeed = onOpenShortsFeed,
                    )
                }
            }
        }
    }
}

// Deliberately not data/model's VideoHistoryEntry.toVideo(): that one carries isShort, which would
// reroute a resumed short to the Shorts player instead of the video player.
internal fun VideoHistoryEntry.toResumeVideo(): Video =
    Video(
        id = videoId,
        title = title,
        channelName = channelName,
        channelId = channelId,
        thumbnailUrl = thumbnailUrl,
        duration = (duration / MILLIS_PER_SECOND).toInt(),
        viewCount = 0L,
        uploadDate = "",
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResettableHomePullToRefreshBox(
    resetKey: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    key(resetKey) {
        ShivaayPullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = modifier,
            content = content,
        )
    }
}
