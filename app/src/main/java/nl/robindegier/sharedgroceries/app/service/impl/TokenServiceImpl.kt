package nl.robindegier.sharedgroceries.app.service.impl

import android.content.SharedPreferences
import nl.robindegier.sharedgroceries.app.service.TokenService

/**
 * Created by robindegier on 11/03/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class TokenServiceImpl(private val sharedPreferences: SharedPreferences) : TokenService {
    companion object {
        const val GOOGLE_TOKEN_KEY = "google-token"
    }

    override var token: String
        get() = sharedPreferences.getString(GOOGLE_TOKEN_KEY, null)
        set(value) {
            val edit = sharedPreferences.edit()
            edit.putString(GOOGLE_TOKEN_KEY, value)
            edit.apply()
        }
}