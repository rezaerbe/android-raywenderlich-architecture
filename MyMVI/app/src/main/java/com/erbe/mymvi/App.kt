package com.erbe.mymvi

import android.app.Application
import com.erbe.mymvi.data.db.MovieDatabase
import timber.log.Timber

lateinit var db: MovieDatabase

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        db = MovieDatabase.getInstance(this)
        INSTANCE = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}