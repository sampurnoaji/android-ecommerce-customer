package id.io.android.seller.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.io.android.seller.data.model.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}