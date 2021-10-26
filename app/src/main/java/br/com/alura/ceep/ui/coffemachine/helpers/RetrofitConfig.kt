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
      // url de conexao base
      .baseUrl("https://api.vertuoguide.click/")
      // converter para coroutines e android
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      // converter para ler os dados da API e transformar em JSON
      .addConverterFactory(GsonConverterFactory.create())
      // client para trafegar os dados
      .client(
        OkHttpClient.Builder()
          // toda chamada que passa por aqui é verificada pelo auth interceptor
          .addInterceptor(AuthInterceptor(context))
          // authenticar automaticamente recebe a chamada de 401 e trata de acordo com o que dentro dele
          .authenticator(TokenAuthenticator())
          .build()
      )
      .build()
  }
}