package nl.robindegier.sharedgroceries.app.di

import android.content.Context
import nl.robindegier.sharedgroceries.app.service.impl.NetworkServiceImpl
import nl.robindegier.sharedgroceries.app.service.NetworkService
import nl.robindegier.sharedgroceries.app.service.TokenService
import nl.robindegier.sharedgroceries.app.service.impl.TokenServiceImpl
import nl.robindegier.sharedgroceries.app.util.StringProvider
import nl.robindegier.sharedgroceries.app.view.googlelogin.GoogleLoginPresenterImpl
import nl.robindegier.sharedgroceries.app.view.googlelogin.GoogleLoginPresenter
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext


/**
 * Created by robindegier on 11/03/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class Modules {
    private val secretModule: Module = applicationContext {
        factory (DiConstants.GOOGLE_CLIENT_ID) { get<StringProvider>().googleClientId() }
    }

    private val networkModule: Module = applicationContext {
        factory { NetworkServiceImpl() as NetworkService }
        factory { TokenServiceImpl(get()) as TokenService }
    }

    private val mvpModule: Module = applicationContext {
        factory { GoogleLoginPresenterImpl(get(), get(), get()) as GoogleLoginPresenter }
    }

    private val utilModule: Module = applicationContext {
        factory { StringProvider(get()) }
        bean { get<Context>().getSharedPreferences("shared-groceries", Context.MODE_PRIVATE) }
    }

    fun getModules(): List<Module> {
        return listOf(secretModule, networkModule, mvpModule, utilModule)
    }
}