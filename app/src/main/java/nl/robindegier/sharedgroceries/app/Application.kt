package nl.robindegier.sharedgroceries.app

import android.app.Application
import timber.log.Timber

/**
 * Created by robindegier on 17/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class Application() : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}