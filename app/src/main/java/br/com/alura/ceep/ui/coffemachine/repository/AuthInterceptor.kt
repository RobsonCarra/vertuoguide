package br.com.alura.ceep.ui.coffemachine.repository

import android.content.Context
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import com.auth0.android.jwt.JWT
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import org.threeten.bp.Instant

class AuthInterceptor(val context: Context) : Interceptor {
  override fun intercept(chain: Chain): Response {
    var request = chain.request()
    SharedPref(context).getString(SharedPref.TOKEN)?.let { token ->
      request = request.newBuilder().addHeader(AUTHORIZATION, token).build()
      return chain.proceed(request)
    } ?: request.newBuilder().addHeader(AUTHORIZATION, "Invalid").build()
    return chain.proceed(request)
  }

  companion object {
    const val AUTHORIZATION = "Authorization"
  }
}