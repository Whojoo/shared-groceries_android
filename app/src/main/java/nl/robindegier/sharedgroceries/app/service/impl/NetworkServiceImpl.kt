package nl.robindegier.sharedgroceries.app.service.impl

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener
import com.androidnetworking.interfaces.OkHttpResponseListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.SingleSubject
import nl.robindegier.sharedgroceries.app.BuildConfig
import nl.robindegier.sharedgroceries.app.dto.JwtDto
import nl.robindegier.sharedgroceries.app.exception.UnauthorizedException
import nl.robindegier.sharedgroceries.app.service.NetworkService
import nl.robindegier.sharedgroceries.app.service.NetworkService.Companion.VERIFY_ERROR
import nl.robindegier.sharedgroceries.app.service.NetworkService.Companion.VERIFY_FAILED
import nl.robindegier.sharedgroceries.app.service.NetworkService.Companion.VERIFY_SUCCESS
import okhttp3.Response
import timber.log.Timber

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class NetworkServiceImpl : NetworkService {
    override fun verifyToken(idToken: String) : Single<Int> {
        val singleSubject = SingleSubject.create<Int>()
        AndroidNetworking.post("${BuildConfig.SERVER_URL}/oauth/google")
                .setPriority(Priority.HIGH)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Authorization", "Bearer $idToken")
                .build()
                .getAsOkHttpResponse(object : OkHttpResponseListener {
                    override fun onResponse(response: Response?) {
                        if (response != null && response.isSuccessful) {
                            singleSubject.onSuccess(VERIFY_SUCCESS)
                        } else {
                            singleSubject.onSuccess(VERIFY_FAILED)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Timber.e(anError?.cause, "error ${anError?.errorBody}")
                        singleSubject.onSuccess(VERIFY_ERROR)
                    }
                })

        return singleSubject.observeOn(AndroidSchedulers.mainThread())
    }

    override fun googleLogin(idToken: String): Single<JwtDto> {
        val singleSubject = SingleSubject.create<JwtDto>()
        AndroidNetworking.post("${BuildConfig.SERVER_URL}/oauth/google")
                .setPriority(Priority.HIGH)
                .addHeaders("Content-type", "application/json")
                .addHeaders("Authorization", "Bearer $idToken")
                .build()
                .getAsOkHttpResponseAndObject(JwtDto::class.java, object: OkHttpResponseAndParsedRequestListener<JwtDto?> {
                    override fun onResponse(response: Response?, jwtDto: JwtDto?) {
                        if (response != null && response.isSuccessful && jwtDto != null) {
                            singleSubject.onSuccess(jwtDto)
                        } else {
                            singleSubject.onError(UnauthorizedException("Unauthorized with given token"))
                        }
                    }

                    override fun onError(anError: ANError?) {
                        singleSubject.onError(anError!!)
                    }
                })

        return singleSubject.observeOn(AndroidSchedulers.mainThread())
    }
}