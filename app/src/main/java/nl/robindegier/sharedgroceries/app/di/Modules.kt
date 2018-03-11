package nl.robindegier.sharedgroceries.app.di

import nl.robindegier.sharedgroceries.app.service.impl.NetworkServiceImpl
import nl.robindegier.sharedgroceries.app.service.NetworkService
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by robindegier on 11/03/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class Modules {
    private val secretModule : Module = applicationContext {
        bean (GOOGLE_CLIENT_ID) { StringProvider(get()).googleClientId() }
    }

    private val networkModule: Module = applicationContext {
        bean { NetworkServiceImpl() as NetworkService }
    }

    private val mvpModule: Module = applicationContext {
        bean { GoogleLoginPresenterImpl(get(), get()) as GoogleLoginPresenter }
    }

    fun getModules() {
        return listOf(secretModule, networkModule, mvpModule)
    }
}