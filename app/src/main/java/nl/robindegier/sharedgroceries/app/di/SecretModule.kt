package nl.robindegier.sharedgroceries.app.di

import nl.robindegier.sharedgroceries.app.di.DiConstants.Companion.GOOGLE_CLIENT_ID
import nl.robindegier.sharedgroceries.app.util.StringProvider
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */

val secretModule : Module = applicationContext {
    provide(GOOGLE_CLIENT_ID) { StringProvider(get()).googleClientId() }
}
