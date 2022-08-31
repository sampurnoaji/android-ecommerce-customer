package id.io.android.olebsai.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.io.android.olebsai.data.model.entity.UserEntity
import id.io.android.olebsai.data.model.entity.product.ProductEntity
import id.io.android.olebsai.data.source.local.product.ProductDao
import id.io.android.olebsai.data.source.local.user.UserDao

@Database(entities = [UserEntity::class, ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
}