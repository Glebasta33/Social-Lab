package com.trusov.sociallab.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trusov.sociallab.domain.entity.Respondent

@Database(entities = [Respondent::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun enterDao(): EnterDao

    companion object {
        private const val DB_NAME = "app.db"
        private val LOCK = Any()
        private var instance: AppDatabase? = null

        fun getInstance(application: Application): AppDatabase {
            instance?.let {
                return it
            }
            synchronized(LOCK) {
                instance?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                instance = db
                return db
            }
        }
    }

}