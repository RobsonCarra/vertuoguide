package br.com.alura.ceep.ui.coffemachine.repository

import android.content.Context
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import com.auth0.android.jwt.JWT
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import org.threeten.bp.Instant

class AuthInterceptor(val context: Context) : Interceptor {
  override fun intercept(chain: Chain): Response {
    var request = chain.request()
    SharedPref(context).getString(SharedPref.TOKEN)?.let { token ->
      JWT(token).expiresAt?.let { expiresAt ->
        if (expiresAt.time > Instant.now().toEpochMilli()) {
          request = request.newBuilder().addHeader(SharedPref.TOKEN, token).build()
          return chain.proceed(request)
        }

        renewToken { renewed ->
          request = request.newBuilder().addHeader(SharedPref.TOKEN, renewed).build()
        }
      }
    }
    return chain.proceed(request)
  }

  private fun renewToken(renewed: (token: String) -> Unit) {
    FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnCompleteListener { result ->
      if (result.isSuccessful) {
        result.result?.token?.let { token ->
          renewed.invoke(token)
        } ?: renewed.invoke("")
      } else {
        renewed.invoke("")
      }
    } ?: renewed.invoke("")
  }

  companion object {
    const val AUTHORIZATION = "Authorization"
  }
}