package io.github.shivaay.shivaaytube.ui.components.layout.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaaySearchField

/**
 * Search variant of [ShivaayTopBar]: back button, inline field, optional trailing actions.
 *
 * Used for in-place filtering of a screen's own content (Settings search, Subscriptions manage
 * mode), not for the Search tab. Global actions are never shown — the user is in a focused mode and
 * the leading affordance dismisses it rather than navigating back.
 */
@Composable
fun ShivaaySearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    autoFocus: Boolean = true,
    releaseFocusWithKeyboard: Boolean = false,
    windowInsets: WindowInsets = ShivaayTopBarDefaults.WindowInsets,
) {
    val focusRequester = remember { FocusRequester() }

    if (autoFocus) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }

    ShivaayTopBar(
        modifier = modifier,
        title = {
            ShivaaySearchField(
                query = query,
                onQueryChange = onQueryChange,
                placeholder = placeholder,
                modifier = Modifier.fillMaxWidth(),
                focusRequester = focusRequester,
                releaseFocusWithKeyboard = releaseFocusWithKeyboard,
            )
        },
        onBack = onClose,
        actions = actions,
        globalActions = ShivaayGlobalActionsMode.None,
        windowInsets = windowInsets,
    )
}
