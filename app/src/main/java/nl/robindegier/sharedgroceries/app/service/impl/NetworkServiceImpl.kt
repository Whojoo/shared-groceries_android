package nl.robindegier.sharedgroceries.app.service.impl

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseListener
import nl.robindegier.sharedgroceries.app.BuildConfig
import nl.robindegier.sharedgroceries.app.dto.GoogleSignInTokenDto
import nl.robindegier.sharedgroceries.app.service.NetworkService
import okhttp3.Response
import timber.log.Timber

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class NetworkServiceImpl : NetworkService {
    override fun verifyToken(idToken: String) {
        val body = GoogleSignInTokenDto(idToken, "unknown")
        AndroidNetworking.post("${BuildConfig.SERVER_URL}/oauth/google")
                .setPriority(Priority.HIGH)
                .addApplicationJsonBody(body)
                .build()
                .getAsOkHttpResponse(object : OkHttpResponseListener {
                    override fun onResponse(response: Response?) {
                        Timber.d("response")
                    }

                    override fun onError(anError: ANError?) {
                        Timber.e(anError?.cause, "error ${anError?.errorBody}")
                    }
                })
    }
}