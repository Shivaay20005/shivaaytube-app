package io.github.shivaay.shivaaytube.ui.screens.onboarding

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.shivaay.shivaaytube.data.backup.ImportKind
import io.github.shivaay.shivaaytube.data.backup.ImportSource
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaayNavRow
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaayRowGroup
import io.github.shivaay.shivaaytube.ui.components.shared.ShivaaySheetHeader
import io.github.shivaay.shivaaytube.ui.components.shared.flowRowGroupShape
import io.github.shivaay.shivaaytube.ui.components.shared.rememberFlowSheetState

private val SheetBottomPadding = 16.dp

/** Everything [source] can import, the same list Settings shows under that app. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ImportSourceSheet(
    source: ImportSource,
    onImport: (ImportKind) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberFlowSheetState(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        ShivaaySheetHeader(
            title = stringResource(source.titleRes),
            onClose = onDismiss,
            showDragHandle = false,
        )
        ShivaayRowGroup(Modifier.verticalScroll(rememberScrollState()).padding(bottom = SheetBottomPadding)) {
            source.kinds.forEachIndexed { index, kind ->
                ShivaayNavRow(
                    title = stringResource(kind.titleRes),
                    supportingText = stringResource(kind.descriptionRes),
                    leadingIcon = Icons.Outlined.FileOpen,
                    showChevron = false,
                    shape = flowRowGroupShape(index, source.kinds.size),
                    onClick = { onImport(kind) },
                )
            }
        }
    }
}
