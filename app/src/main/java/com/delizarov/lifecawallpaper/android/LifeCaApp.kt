package com.delizarov.lifecawallpaper.android

import android.app.Application
import com.delizarov.lifecawallpaper.di.AppComponent
import com.delizarov.lifecawallpaper.di.DaggerAppComponent
import com.delizarov.lifecawallpaper.domain.WallpaperSettings

class LifeCaApp: Application() {

    internal lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val settings = readSettings()

        DaggerAppComponent.builder()
            .bindAppContext(this)
            .bindSettings(settings)
            .build()
    }

    private fun readSettings(): WallpaperSettings {
        return WallpaperSettings.Default
    }
}