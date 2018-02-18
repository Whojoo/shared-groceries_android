package nl.robindegier.sharedgroceries.app.di

import nl.robindegier.sharedgroceries.app.service.NetworkService
import nl.robindegier.sharedgroceries.app.service.impl.NetworkServiceImpl
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */

val networkModule: Module = applicationContext {
    provide { NetworkServiceImpl() as NetworkService }
}