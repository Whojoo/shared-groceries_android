package nl.robindegier.sharedgroceries.app.view.googlelogin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.activity_google_login.*
import nl.robindegier.sharedgroceries.app.R
import nl.robindegier.sharedgroceries.app.view.list.listActivityIntent
import org.koin.android.ext.android.inject

class GoogleLoginActivity : AppCompatActivity(), GoogleLoginView {

    private val presenter: GoogleLoginPresenter by inject()

    override fun context() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        presenter.attach(this)

        sign_in_button.setSize(SignInButton.SIZE_WIDE)
        sign_in_button.setOnClickListener { presenter.signInPressed() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()

        presenter.start()
    }

    override fun startForResult(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun goToList() {
        startActivity(listActivityIntent())
    }

    override fun showVerifyFailed() {
        Snackbar.make(sign_in_button, "Verify failed", Snackbar.LENGTH_SHORT).show()
    }

    override fun showVerifyError() {
        Snackbar.make(sign_in_button, "Verify Error", Snackbar.LENGTH_SHORT).show()
    }
}
