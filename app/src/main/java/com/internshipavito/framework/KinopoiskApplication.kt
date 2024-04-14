package com.internshipavito.framework

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class KinopoiskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this )
    }
}