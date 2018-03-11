package nl.robindegier.sharedgroceries.app

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.jacksonandroidnetworking.JacksonParserFactory
import nl.robindegier.sharedgroceries.app.di.Modules
import org.koin.android.ext.android.startKoin
import timber.log.Timber

@Suppress("unused")
/**
 * Created by robindegier on 17/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin(this, Modules().getModules())

        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.setParserFactory(JacksonParserFactory())
    }
}