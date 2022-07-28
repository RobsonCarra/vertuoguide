package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.AnalyticsHelper
import br.com.alura.ceep.ui.coffemachine.presentation.Login.view.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

  private lateinit var coffeToolbar: Toolbar
  private lateinit var putPassword: TextInputEditText
  private lateinit var putConfirmPassword: TextInputEditText
  private lateinit var putEmail: TextInputEditText
  private lateinit var auth: FirebaseAuth
  private lateinit var createAccount: Button
  private lateinit var progressBar: ProgressBar
  private val analyticsHelper: AnalyticsHelper by lazy {
    AnalyticsHelper(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.register_activity)
    setup()
    auth = FirebaseAuth.getInstance()
    listeners()
    analyticsHelper.log(AnalyticsHelper.REGISTER_OPENED)
  }

  private fun setup() {
    coffeToolbar = findViewById(R.id.coffe_toolbar)
    setSupportActionBar(coffeToolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    putConfirmPassword = findViewById(R.id.confirm_password)
    putPassword = findViewById(R.id.put_password)
    putEmail = findViewById(R.id.confirm_email)
    createAccount = findViewById(R.id.create_account_button)
    progressBar = findViewById(R.id.progress_bar_register_activity)
  }

  private fun goToLoginActivity() {
    analyticsHelper.log(AnalyticsHelper.REGISTER_GO_TO_LOGIN)
    val intent = Intent(this, LoginActivity::class.java)
    this.startActivity(intent)
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener {
      analyticsHelper.log(AnalyticsHelper.REGISTER_RETURNED)
      onBackPressed()
    }

    createAccount.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.REGISTER_CREATE_ACCOUNT)
      val email = putEmail.text.toString()
      val password = putPassword.text.toString()
      val confirmPassword = putConfirmPassword.text.toString()
      if (email.isEmpty()) {
        putEmail.error = getString(R.string.enter_email)
        return@setOnClickListener
      }
      putEmail.requestFocus()
      if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        putEmail.error = getString(R.string.enter_correct_email)
        putEmail.requestFocus()
        return@setOnClickListener
      }
      if (password.isEmpty()) {
        putPassword.error = getString(R.string.enter_password)
        putPassword.requestFocus()
      }
      if (confirmPassword.isEmpty()) {
        putConfirmPassword.error = getString(R.string.enter_password_confirmation)
        return@setOnClickListener
      }
      if (password == confirmPassword) {
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener(this) { task ->
            progressBar.visibility = View.INVISIBLE
            if (task.isSuccessful) {
              Toast.makeText(
                this@RegisterActivity,
                getString(R.string.correctly_created_user),
                Toast.LENGTH_SHORT
              )
                .show()
              analyticsHelper.log(AnalyticsHelper.REGISTER_CREATE_ACCOUNT_SUCESS)
              goToLoginActivity()
            } else {
              Toast.makeText(
                this@RegisterActivity,
                getString(R.string.unauthorized),
                Toast.LENGTH_SHORT
              ).show()
              analyticsHelper.log(AnalyticsHelper.REGISTER_CREATE_ACCOUNT_ERROR)
            }
          }
      } else {
        progressBar.visibility = View.INVISIBLE
        Toast.makeText(
          this@RegisterActivity,
          getString(R.string.passwords_different),
          Toast.LENGTH_SHORT
        )
          .show()
      }
    }
  }
}