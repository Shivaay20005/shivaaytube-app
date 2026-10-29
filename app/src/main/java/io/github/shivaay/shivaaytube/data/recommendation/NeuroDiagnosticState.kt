/*
 * Copyright (C) 2025-2026 ShivaayTube | A-EDev
 *
 * This file is part of ShivaayTube (https://github.com/Shivaay20005/shivaaytube-app).
 *
 * This recommendation algorithm (ShivaayNeuroEngine) is the intellectual property
 * of the ShivaayTube project. Any use of this code in other projects must
 * explicitly credit "ShivaayTube Android Client" and link back to the original repository.
 */

package io.github.shivaay.shivaaytube.data.recommendation

import kotlinx.serialization.json.JsonElement

/** Everything a rank pass reads besides its candidates, as a Home snapshot stores it. */
internal data class NeuroDiagnosticState(
    val brain: JsonElement,
    val categories: NeuroCategoryStore.CategoryIndex,
    val coWatch: NeuroCoWatchStore.CoWatchIndex,
)
