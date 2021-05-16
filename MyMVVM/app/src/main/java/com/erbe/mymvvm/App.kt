package com.erbe.mymvvm

import android.app.Application
import com.erbe.mymvvm.data.db.MovieDatabase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

lateinit var db: MovieDatabase

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        db = MovieDatabase.getInstance(this)
        INSTANCE = this
        this.initCicerone()
    }

    private fun initCicerone() {
        this.cicerone = Cicerone.create()
    }
}