package io.github.shivaay.shivaaytube.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.shivaay.shivaaytube.data.local.AppDatabase
import io.github.shivaay.shivaaytube.data.local.dao.NotificationDao
import io.github.shivaay.shivaaytube.data.local.dao.PlaylistDao
import io.github.shivaay.shivaaytube.data.local.dao.VideoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = AppDatabase.getDatabase(context)

    @Provides
    fun provideVideoDao(database: AppDatabase): VideoDao = database.videoDao()

    @Provides
    fun providePlaylistDao(database: AppDatabase): PlaylistDao = database.playlistDao()

    @Provides
    fun provideNotificationDao(database: AppDatabase): NotificationDao = database.notificationDao()

    @Provides
    fun provideNoteDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.NoteDao = database.noteDao()

    @Provides
    fun provideCacheDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.CacheDao = database.cacheDao()

    @Provides
    fun provideHomeFeedCacheDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.HomeFeedCacheDao = database.homeFeedCacheDao()

    @Provides
    fun provideDownloadDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.DownloadDao = database.downloadDao()

    @Provides
    fun provideDownloadCollectionDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.DownloadCollectionDao =
        database.downloadCollectionDao()

    @Provides
    fun provideRecognitionHistoryDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.RecognitionHistoryDao =
        database.recognitionHistoryDao()

    @Provides
    fun provideSubscriptionGroupDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.SubscriptionGroupDao =
        database.subscriptionGroupDao()

    @Provides
    fun provideWatchHistoryDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.WatchHistoryDao = database.watchHistoryDao()

    @Provides
    fun provideSyncLogDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.SyncLogDao = database.syncLogDao()

    @Provides
    fun provideSyncPeerDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.SyncPeerDao = database.syncPeerDao()

    @Provides
    fun provideMusicGraphDao(database: AppDatabase): io.github.shivaay.shivaaytube.data.local.dao.MusicGraphDao = database.musicGraphDao()
}
