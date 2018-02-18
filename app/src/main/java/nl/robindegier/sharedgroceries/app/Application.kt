package nl.robindegier.sharedgroceries.app

import android.app.Application
import nl.robindegier.sharedgroceries.app.di.mvpModule
import nl.robindegier.sharedgroceries.app.di.networkModule
import nl.robindegier.sharedgroceries.app.di.secretModule
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

        startKoin(this, listOf(networkModule, mvpModule, secretModule))
    }
}