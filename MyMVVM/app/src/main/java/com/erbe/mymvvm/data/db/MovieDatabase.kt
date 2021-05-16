package com.erbe.mymvvm.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.erbe.mymvvm.data.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreIdConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "MovieDatabase"
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(application: Application): MovieDatabase {
            synchronized(MovieDatabase.lock) {
                if (MovieDatabase.INSTANCE == null) {
                    MovieDatabase.INSTANCE =
                        Room.databaseBuilder(
                          application,
                          MovieDatabase::class.java,
                          MovieDatabase.DB_NAME
                        )
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}