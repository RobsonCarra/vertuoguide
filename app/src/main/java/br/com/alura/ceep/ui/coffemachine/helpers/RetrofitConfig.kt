package br.com.alura.ceep.ui.coffemachine.helpers

import android.content.Context
import br.com.alura.ceep.ui.coffemachine.repository.AuthInterceptor
import br.com.alura.ceep.ui.coffemachine.repository.TokenAuthenticator
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
  fun getClient(context: Context): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://api.vertuoguide.click/")
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create())
      .client(
        OkHttpClient.Builder()
          .authenticator(TokenAuthenticator())
          .addInterceptor(AuthInterceptor(context))
          .build()
      )
      .build()
  }
}