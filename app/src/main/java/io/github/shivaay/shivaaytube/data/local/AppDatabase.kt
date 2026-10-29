package io.github.shivaay.shivaaytube.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import io.github.shivaay.shivaaytube.data.local.dao.CacheDao
import io.github.shivaay.shivaaytube.data.local.dao.DownloadCollectionDao
import io.github.shivaay.shivaaytube.data.local.dao.DownloadDao
import io.github.shivaay.shivaaytube.data.local.dao.HomeFeedCacheDao
import io.github.shivaay.shivaaytube.data.local.dao.MusicGraphDao
import io.github.shivaay.shivaaytube.data.local.dao.NoteDao
import io.github.shivaay.shivaaytube.data.local.dao.NotificationDao
import io.github.shivaay.shivaaytube.data.local.dao.PlaylistDao
import io.github.shivaay.shivaaytube.data.local.dao.RecognitionHistoryDao
import io.github.shivaay.shivaaytube.data.local.dao.SubscriptionGroupDao
import io.github.shivaay.shivaaytube.data.local.dao.SyncLogDao
import io.github.shivaay.shivaaytube.data.local.dao.SyncPeerDao
import io.github.shivaay.shivaaytube.data.local.dao.VideoDao
import io.github.shivaay.shivaaytube.data.local.dao.WatchHistoryDao
import io.github.shivaay.shivaaytube.data.local.entity.DownloadCollectionEntity
import io.github.shivaay.shivaaytube.data.local.entity.DownloadCollectionItemEntity
import io.github.shivaay.shivaaytube.data.local.entity.DownloadEntity
import io.github.shivaay.shivaaytube.data.local.entity.DownloadItemEntity
import io.github.shivaay.shivaaytube.data.local.entity.HomeFeedCacheEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicGraphAlbumEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicGraphArtistEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicGraphEdgeEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicGraphPlaylistEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicGraphTrackEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicHomeCacheEntity
import io.github.shivaay.shivaaytube.data.local.entity.MusicHomeChipEntity
import io.github.shivaay.shivaaytube.data.local.entity.NoteEntity
import io.github.shivaay.shivaaytube.data.local.entity.NotificationEntity
import io.github.shivaay.shivaaytube.data.local.entity.PlaylistEntity
import io.github.shivaay.shivaaytube.data.local.entity.PlaylistVideoCrossRef
import io.github.shivaay.shivaaytube.data.local.entity.RecognitionHistoryEntity
import io.github.shivaay.shivaaytube.data.local.entity.SubscriptionFeedEntity
import io.github.shivaay.shivaaytube.data.local.entity.SubscriptionGroupEntity
import io.github.shivaay.shivaaytube.data.local.entity.SyncLogEntity
import io.github.shivaay.shivaaytube.data.local.entity.SyncPeerEntity
import io.github.shivaay.shivaaytube.data.local.entity.VideoEntity
import io.github.shivaay.shivaaytube.data.local.entity.WatchHistoryEntity
import io.github.shivaay.shivaaytube.data.local.migrations.MIGRATIONS
import io.github.shivaay.shivaaytube.data.local.migrations.Migration24To25

@Database(
    entities = [
        VideoEntity::class,
        PlaylistEntity::class,
        PlaylistVideoCrossRef::class,
        NotificationEntity::class,
        SubscriptionFeedEntity::class,
        MusicHomeCacheEntity::class,
        MusicHomeChipEntity::class,
        DownloadEntity::class,
        DownloadItemEntity::class,
        WatchHistoryEntity::class,
        HomeFeedCacheEntity::class,
        SubscriptionGroupEntity::class,
        RecognitionHistoryEntity::class,
        SyncLogEntity::class,
        SyncPeerEntity::class,
        MusicGraphTrackEntity::class,
        MusicGraphArtistEntity::class,
        MusicGraphAlbumEntity::class,
        MusicGraphPlaylistEntity::class,
        MusicGraphEdgeEntity::class,
        NoteEntity::class,
        DownloadCollectionEntity::class,
        DownloadCollectionItemEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 24, to = 25, spec = Migration24To25::class),
        AutoMigration(from = 25, to = 26),
        AutoMigration(from = 26, to = 27),
        AutoMigration(from = 27, to = 28),
        AutoMigration(from = 28, to = 29),
        AutoMigration(from = 29, to = 30),
        AutoMigration(from = 30, to = 31),
        AutoMigration(from = 31, to = 32),
        AutoMigration(from = 32, to = 33),
    ],
    version = 33,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun notificationDao(): NotificationDao

    abstract fun noteDao(): NoteDao

    abstract fun cacheDao(): CacheDao

    abstract fun downloadDao(): DownloadDao

    abstract fun downloadCollectionDao(): DownloadCollectionDao

    abstract fun watchHistoryDao(): WatchHistoryDao

    abstract fun homeFeedCacheDao(): HomeFeedCacheDao

    abstract fun subscriptionGroupDao(): SubscriptionGroupDao

    abstract fun recognitionHistoryDao(): RecognitionHistoryDao

    abstract fun syncLogDao(): SyncLogDao

    abstract fun syncPeerDao(): SyncPeerDao

    abstract fun musicGraphDao(): MusicGraphDao

    companion object {
        @Volatile
        @Suppress("ktlint:standard:property-naming")
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "shivaay_database",
                    ).addMigrations(*MIGRATIONS)
                        .fallbackToDestructiveMigration(false)
                        .build()
                INSTANCE = instance
                instance
            }
    }
}
