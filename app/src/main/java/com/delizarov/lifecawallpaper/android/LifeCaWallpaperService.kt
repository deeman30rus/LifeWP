package com.delizarov.lifecawallpaper.android

import android.content.Intent
import android.graphics.Canvas
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.lifecycle.*
import com.delizarov.lifecawallpaper.di.AppComponent
import com.delizarov.lifecawallpaper.presentation.FrameRenderer
import com.delizarov.lifecawallpaper.presentation.LifeViewModel
import com.delizarov.lifecawallpaper.presentation.ViewStateDiff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class LifeCaWallpaperService: WallpaperService() {

    private var appComponent: AppComponent? = null

    private val vm: LifeViewModel
        get() {
            return requireNotNull(appComponent?.viewModel)
        }

    private val frameRenderer: FrameRenderer
        get() {
            return requireNotNull(appComponent?.frameRenderer)
        }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appComponent = (applicationContext as? LifeCaApp)?.appComponent

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreateEngine(): Engine {
        return WallpaperEngine()
    }

    private open inner class CoroutineHolderEngine: Engine(), LifecycleOwner {

        private val lifecycleRegistry = LifecycleRegistry(this@CoroutineHolderEngine)

        private var _scope: CoroutineScope? = null
        protected val engineScope: CoroutineScope
            get() {
                return _scope ?: createScope().also { _scope = it }
            }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)

            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)

            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)

            if (visible) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            } else {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            }
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

        private fun createScope(): CoroutineScope {
            val context = SupervisorJob() + Dispatchers.Main
            val scope = CoroutineScope(context)

            lifecycle.addObserver(object: LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
                        lifecycle.removeObserver(this)

                        scope.cancel()
                    }
                }
            })

            return scope
        }
    }

    private inner class WallpaperEngine: CoroutineHolderEngine() {

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)

            // как будто преобразование до кадров - это не часть вью модели

            vm
                .diffFlow()
                .onEach(::renderFrame)
                .launchIn(engineScope)
        }

        private fun renderFrame(diff: ViewStateDiff) {
            val holder = surfaceHolder
            var canvas: Canvas? = null

            try {
                holder.lockCanvas()?.also {
                    canvas = it
                    frameRenderer.renderFrame(diff, it)
                }
            } finally {
                canvas?.let { holder.unlockCanvasAndPost(canvas) }
            }
        }
    }
}