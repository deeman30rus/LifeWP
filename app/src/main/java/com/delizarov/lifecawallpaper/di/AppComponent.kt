package com.delizarov.lifecawallpaper.di

import android.content.Context
import com.delizarov.lifecawallpaper.domain.WallpaperSettings
import com.delizarov.lifecawallpaper.presentation.FrameRenderer
import com.delizarov.lifecawallpaper.presentation.LifeViewModel
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {

    val viewModel: LifeViewModel

    val frameRenderer: FrameRenderer

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindAppContext(appContext: Context): Builder

        @BindsInstance
        fun bindSettings(settings: WallpaperSettings): Builder

        fun build(): AppComponent
    }
}