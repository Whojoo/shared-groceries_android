package nl.robindegier.sharedgroceries.app.util

import android.content.Context
import nl.robindegier.sharedgroceries.app.R

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class StringProvider(private val context: Context) {
    fun googleClientId() = context.getString(R.string.google_client_id)!!
}