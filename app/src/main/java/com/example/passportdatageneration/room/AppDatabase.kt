package com.example.passportdatageneration.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passportdatageneration.room.dao.NationsDao
import com.example.passportdatageneration.room.entity.Nations

@Database(entities = [Nations::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun nationsDao(): NationsDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, "nations.db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}