package io.github.shivaay.flow.widget.core

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.github.shivaay.flow.widget.core.refresh.WidgetContentSync
import io.github.shivaay.flow.widget.downloads.DownloadsSource
import io.github.shivaay.flow.widget.onrepeat.OnRepeatSource
import io.github.shivaay.flow.widget.playlist.PlaylistWidgetSource
import io.github.shivaay.flow.widget.recent.RecentlyPlayedSource
import io.github.shivaay.flow.widget.week.WeekSource

/**
 * Glance widgets can't use constructor injection (the framework instantiates them),
 * so Hilt singletons are reached through this entry point.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun recentlyPlayedSource(): RecentlyPlayedSource

    fun downloadsSource(): DownloadsSource

    fun onRepeatSource(): OnRepeatSource

    fun playlistWidgetSource(): PlaylistWidgetSource

    fun weekSource(): WeekSource

    fun widgetContentSync(): WidgetContentSync
}

fun widgetEntryPoint(context: Context): WidgetEntryPoint =
    EntryPointAccessors.fromApplication(context.applicationContext, WidgetEntryPoint::class.java)
