package nl.robindegier.sharedgroceries.app.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindString
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_google_login.*
import nl.robindegier.sharedgroceries.app.R
import timber.log.Timber

class GoogleLoginActivity : AppCompatActivity() {
    companion object {
        const val RC_GOOGLE_SIGN_IN = 20
    }

    @BindString(R.string.grocery_uaa_client_id)
    private lateinit var uaaClientId: String
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        ButterKnife.bind(this)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestIdToken(uaaClientId)
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        sign_in_button.setSize(SignInButton.SIZE_WIDE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Timber.i("On start has tried to grab an account!")
    }

    @OnClick(R.id.sign_in_button)
    internal fun onSignInClick() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            val account = task?.getResult(ApiException::class.java)
            Timber.i("Sign in success!")
        } catch (e: ApiException) {
            Timber.e(e, "Sign in failed")
        }
    }
}
