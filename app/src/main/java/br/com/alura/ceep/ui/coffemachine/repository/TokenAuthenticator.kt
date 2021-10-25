package br.com.alura.ceep.ui.coffemachine.repository

import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    val renewed = renewToken()
    return response.request().newBuilder().header(AUTHORIZATION, renewed).build()
  }

  private fun renewToken(): String {
    val task = FirebaseAuth.getInstance().currentUser?.getIdToken(true)
    if (task == null) {
      return "Invalid"
    }
    val result = Tasks.await(task)
    return result?.token ?: "Invalid"
  }

  companion object {
    const val AUTHORIZATION = "Authorization"
  }
}