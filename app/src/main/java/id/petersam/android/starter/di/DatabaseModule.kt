package id.petersam.android.starter.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.petersam.android.starter.data.source.local.UserDao
import id.petersam.android.starter.data.source.local.AppDatabase
import id.petersam.android.starter.data.source.local.DatabaseCallback
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    const val DB_NAME = "app.db"

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context,
        provider: Provider<UserDao>
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .addCallback(DatabaseCallback(context, provider))
//            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideAppDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {}
    }
}