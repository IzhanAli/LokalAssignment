package com.lokalassignment.applokal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lokalassignment.applokal.models.JobEntity
import com.lokalassignment.applokal.util.JobDAO
@Database(entities = [JobEntity::class], version = 1)
abstract class JobsDatabase: RoomDatabase() {

    abstract fun jobDAO(): JobDAO

    companion object {
        @Volatile private var INSTANCE: JobsDatabase? = null

        fun getDatabase(context: Context): JobsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobsDatabase::class.java,
                    "myjobs",
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}

