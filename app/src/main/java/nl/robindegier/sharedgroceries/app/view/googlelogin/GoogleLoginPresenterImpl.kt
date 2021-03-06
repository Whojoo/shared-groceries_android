package nl.robindegier.sharedgroceries.app.view.googlelogin

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import nl.robindegier.sharedgroceries.app.exception.UnauthorizedException
import nl.robindegier.sharedgroceries.app.service.NetworkService
import nl.robindegier.sharedgroceries.app.service.TokenService
import nl.robindegier.sharedgroceries.app.view.googlelogin.GoogleLoginPresenter.Companion.RC_GOOGLE_SIGN_IN
import timber.log.Timber

/**
 * Created by robindegier on 18/02/2018.
 * Copyright © 2018 DearNova. All rights reserved.
 */
class GoogleLoginPresenterImpl(private val networkService: NetworkService, private val googleClientId: String,
                               private val tokenService: TokenService) : GoogleLoginPresenter {
    private var view: GoogleLoginView? = null
    private var googleSignInClient: GoogleSignInClient? = null

    override fun attach(view: GoogleLoginView) {
        this.view = view

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleClientId)
                .build()

        googleSignInClient = GoogleSignIn.getClient(view.context(), googleSignInOptions)
    }

    override fun detach() {
        view = null
        googleSignInClient = null
    }

    override fun signInPressed() {
        view!!.startForResult(googleSignInClient!!.signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun start() {
        val account = GoogleSignIn.getLastSignedInAccount(view!!.context())
        Timber.i("On start has tried to grab an account!")
        handleAccount(account)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            val account = task?.getResult(ApiException::class.java)
            Timber.i("Sign in success!")
            handleAccount(account)
        } catch (e: ApiException) {
            Timber.e(e, "Sign in failed")
        }
    }

    private fun handleAccount(account: GoogleSignInAccount?) {
        account?.let { googleAccount ->
            networkService.googleLogin(googleAccount.idToken!!)
        }?.subscribe({ jwt ->
            tokenService.token = jwt.token
            view!!.goToList()
        }, { error ->
            Timber.e(error, "Error occurred")
            if (error is UnauthorizedException) {
                view!!.showVerifyFailed()
            } else {
                view!!.showVerifyError()
            }
        })
    }
}