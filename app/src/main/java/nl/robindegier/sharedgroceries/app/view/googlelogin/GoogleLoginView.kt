package nl.robindegier.sharedgroceries.app.view.googlelogin

import android.content.Context
import android.content.Intent

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
interface GoogleLoginView {
    fun context() : Context

    fun startForResult(intent: Intent, requestCode: Int)
}