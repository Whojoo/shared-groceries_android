package nl.robindegier.sharedgroceries.app.service

import io.reactivex.Single

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
interface NetworkService {
    companion object {
        const val VERIFY_SUCCESS = 1
        const val VERIFY_FAILED = 2
        const val VERIFY_ERROR = 3
    }

    fun verifyToken(idToken: String) : Single<Int>
}