package br.com.alura.ceep.ui.coffemachine.presentation.Login.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.presentation.DashboardActivity
import br.com.alura.ceep.ui.coffemachine.presentation.HomeFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var putPassword: TextInputEditText
    private lateinit var putEmail: TextInputEditText
    private lateinit var putConfirmPassword: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setup()
        auth = FirebaseAuth.getInstance()
        listeners()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }


    fun createAccount() {
        auth.createUserWithEmailAndPassword(putEmail.toString(), putPassword.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    fun setup() {
        putPassword = findViewById(R.id.password_input)
        putEmail = findViewById(R.id.email_input)
        putConfirmPassword = findViewById(R.id.confirm_password_input)
        loginButton = findViewById(R.id.login_button)
    }

    fun listeners() {
        loginButton.setOnClickListener {
            if (putEmail.toString().isEmpty())
                putEmail.error = "Please enter the e-mail"
            putEmail.requestFocus()
            if (!Patterns.EMAIL_ADDRESS.matcher(putEmail.text.toString()).matches()) {
                putEmail.error = "Please enter valid email"
                putEmail.requestFocus()
                return@setOnClickListener
            }
            if (putPassword.text.toString().isEmpty()) {
                putPassword.error = "Please enter password"
                putPassword.requestFocus()
            }

            auth.signInWithEmailAndPassword(putEmail.toString(), putPassword.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                        val intent = Intent(this, DashboardActivity::class.java)
                        this.startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }
}