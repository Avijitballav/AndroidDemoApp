package com.talki.booki.app.core

import android.app.Application
import android.content.res.Resources
import androidx.multidex.MultiDex
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.talki.booki.app.utils.TinyDB
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TalkiBookiApplication : Application() {
    /*Create TinyDb Singleton Instance*/
    val tinyDB: TinyDB by lazy {
        TinyDB(applicationInstance)
    }

    override fun onCreate() {
        super.onCreate()
        /* if (BuildConfig.DEBUG) {
             Timber.plant(Timber.DebugTree())
         }*/

        applicationInstance = this
        instance = this
        resourses = resources

        MultiDex.install(applicationInstance)
        setupFacebookSDK()
    }

    private fun setupFacebookSDK() {
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    companion object {
        lateinit var applicationInstance: TalkiBookiApplication
        lateinit var instance: Application
        lateinit var resourses: Resources
    }
}