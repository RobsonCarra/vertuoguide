package br.com.alura.ceep.ui.coffemachine.presentation.Login.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.CoffeesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.DashboardActivity
import br.com.alura.ceep.ui.coffemachine.presentation.RegisterActivity
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import br.com.alura.ceep.ui.coffemachine.viewmodel.config.CoffesViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

  private lateinit var putPassword: TextInputEditText
  private lateinit var putEmail: TextInputEditText
  private lateinit var loginButton: Button
  private lateinit var createAccount: Button
  private lateinit var progressBar: ProgressBar

  private val viewModel: CoffeesViewModel by viewModels {
    CoffesViewModelFactory(
      CoffeesRepository(
        CoffeesRoomDataBase.getDatabase(this).coffesDao(),
        RetrofitConfig().getClient(this)
      ),
      FirebaseAuth.getInstance(),
      SharedPref(this)
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login_activity)
    SharedPref(this).getString(SharedPref.TOKEN)?.let { token ->
      if (token.isNotEmpty()) {
        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
        this.startActivity(intent)
      }
    }
    setup()
    listeners()
    observers()
  }

  private fun showLoader(show: Boolean) {
    if (show) {
      progressBar.visibility = View.VISIBLE
      return
    }
    progressBar.visibility = View.GONE
  }

  private fun setup() {
    putPassword = findViewById(R.id.password_input)
    putEmail = findViewById(R.id.email_input)
    loginButton = findViewById(R.id.login_button)
    createAccount = findViewById(R.id.create_account_button)
    progressBar = findViewById(R.id.progress_bar_login_activity)
  }

  private fun observers() {
    lifecycleScope.launch {
      viewModel.showLoader.observe(this@LoginActivity) { show ->
        showLoader(show)
      }
      viewModel.showError.observe(this@LoginActivity) { error ->
        Toast.makeText(this@LoginActivity, getString(error), Toast.LENGTH_SHORT).show()
      }
      viewModel.goToHome.observe(this@LoginActivity) {
        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
      }
    }
  }

  private fun listeners() {
    loginButton.setOnClickListener {
      login()
    }
    createAccount.setOnClickListener {
      register()
    }
  }

  private fun login() {
    val email = putEmail.text.toString()
    val password = putPassword.text.toString()
    if (email.isEmpty()) {
      putEmail.error = getString(R.string.email_required)
      return
    }
    putEmail.requestFocus()
    if (!Patterns.EMAIL_ADDRESS.matcher(putEmail.text.toString()).matches()) {
      putEmail.error = getString(R.string.invalid_email)
      putEmail.requestFocus()
      return
    }
    if (password.isEmpty()) {
      putPassword.error = getString(R.string.password_required)
      putPassword.requestFocus()
    }
    progressBar.visibility = View.VISIBLE
    viewModel.signIn(email, password)
  }

  private fun register() {
    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
    this.startActivity(intent)
  }
}