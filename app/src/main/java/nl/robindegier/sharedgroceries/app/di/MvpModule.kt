package nl.robindegier.sharedgroceries.app.di

import nl.robindegier.sharedgroceries.app.view.googlelogin.GoogleLoginPresenter
import nl.robindegier.sharedgroceries.app.view.googlelogin.GoogleLoginPresenterImpl
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
val mvpModule: Module = applicationContext {
    provide { GoogleLoginPresenterImpl(get(), get()) as GoogleLoginPresenter }
}
