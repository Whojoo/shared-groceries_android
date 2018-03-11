package nl.robindegier.sharedgroceries.app.service.impl

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import nl.robindegier.sharedgroceries.app.dto.GoogleSignInTokenDto
import nl.robindegier.sharedgroceries.app.service.NetworkService
import org.json.JSONObject
import timber.log.Timber

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class NetworkServiceImpl : NetworkService {
    override fun verifyToken(idToken: String) {
        val body = GoogleSignInTokenDto(idToken, "unknown")
        AndroidNetworking.post("192.168.2.5:5000/oauth/google")
                .addBodyParameter(body)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        Timber.d("response")
                    }

                    override fun onError(anError: ANError?) {
                        Timber.d("error")
                    }
                })
    }
}