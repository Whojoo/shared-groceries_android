package nl.robindegier.sharedgroceries.app.view.googlelogin

import android.content.Intent

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
interface GoogleLoginPresenter {
    companion object {
        const val RC_GOOGLE_SIGN_IN = 20
    }

    fun attach(view: GoogleLoginView)

    fun detach()

    fun signInPressed()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun start()
}