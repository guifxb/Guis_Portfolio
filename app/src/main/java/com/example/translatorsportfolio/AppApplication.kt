package com.example.translatorsportfolio

import android.app.Application
import com.example.translatorsportfolio.data.AppContainer
import com.example.translatorsportfolio.data.DefaultAppContainer
import com.google.android.material.color.DynamicColors


class AppApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}