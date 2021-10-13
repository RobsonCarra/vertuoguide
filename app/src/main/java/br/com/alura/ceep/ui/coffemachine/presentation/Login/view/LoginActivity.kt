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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.DashboardActivity
import br.com.alura.ceep.ui.coffemachine.presentation.RegisterActivity
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var putPassword: TextInputEditText
    private lateinit var putEmail: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var createAccount: Button

    //    private lateinit var progressBar: ProgressBar
    private val viewModel: CoffesViewModel by viewModels {
        CoffesViewModel.CoffesViewModelFactory(
            CoffesRepository(
                CoffesRoomDataBase.getDatabase(this).coffesDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setup()
        auth = FirebaseAuth.getInstance()
        listeners()
    }

    public override fun onStart() {
        super.onStart()
    }

    private fun goToHome() {
        val intent = Intent(this, DashboardActivity::class.java)
        this.startActivity(intent)
    }

    private fun goToRegisterActivity() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        this.startActivity(intent)
    }

    private fun setup() {
        putPassword = findViewById(R.id.password_input)
        putEmail = findViewById(R.id.email_input)
        loginButton = findViewById(R.id.login_button)
        createAccount = findViewById(R.id.create_account_button)
//        progressBar = findViewById(R.id.progress_bar_login_activity)
    }

    private fun listeners() {
        loginButton.setOnClickListener {
            val email = putEmail.text.toString()
            val password = putPassword.text.toString()
            if (email.isEmpty()) {
                putEmail.error = "Please enter the e-mail"
                return@setOnClickListener
            }
            putEmail.requestFocus()
            if (!Patterns.EMAIL_ADDRESS.matcher(putEmail.text.toString()).matches()) {
                putEmail.error = "Please enter valid email"
                putEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                putPassword.error = "Please enter password"
                putPassword.requestFocus()
            }
//            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.getIdToken(true)?.addOnCompleteListener { result ->
                            if (result.isSuccessful) {
                                result.result?.token?.let { token ->
                                    SharedPref(this).put(SharedPref.TOKEN, token)
//                                    progressBar.visibility = View.INVISIBLE
                                    goToHome()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Unauthorized", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        createAccount.setOnClickListener {
            goToRegisterActivity()
        }
    }
}