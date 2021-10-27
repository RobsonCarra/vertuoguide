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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.register_activity)
    setup()
    auth = FirebaseAuth.getInstance()
    listeners()
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
    val intent = Intent(this, LoginActivity::class.java)
    this.startActivity(intent)
  }

  private fun listeners() {
    coffeToolbar.setNavigationOnClickListener { arrow: View? ->
      onBackPressed()
    }

    createAccount.setOnClickListener {
      val email = putEmail.text.toString()
      val password = putPassword.text.toString()
      val confirmPassword = putConfirmPassword.text.toString()
      if (email.isEmpty()) {
        putEmail.error = "Please enter the e-mail"
        return@setOnClickListener
      }
      putEmail.requestFocus()
      if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        putEmail.error = "Please enter valid email"
        putEmail.requestFocus()
        return@setOnClickListener
      }
      if (password.isEmpty()) {
        putPassword.error = "Please enter password"
        putPassword.requestFocus()
      }
      if (confirmPassword.isEmpty()) {
        putConfirmPassword.error = "Please enter the password confirmation"
        return@setOnClickListener
      }
      if (password == confirmPassword) {
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener(this) { task ->
            progressBar.visibility = View.INVISIBLE
            if (task.isSuccessful) {
              val user = auth.currentUser
              Toast.makeText(
                this@RegisterActivity,
                "Correctly created user",
                Toast.LENGTH_SHORT
              )
                .show()
              goToLoginActivity()
            } else {
              Toast.makeText(
                this@RegisterActivity,
                "Unauthorized",
                Toast.LENGTH_SHORT
              )
                .show()
            }
          }
      } else {
        progressBar.visibility = View.INVISIBLE
        Toast.makeText(
          this@RegisterActivity,
          "The passwords are different, please enter correctly",
          Toast.LENGTH_SHORT
        )
          .show()
      }
    }
  }
}