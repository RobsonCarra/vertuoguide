package br.com.alura.ceep.ui.coffemachine.repository

import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.CoffeeUser
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeInterface {

  @GET(GET_ALL)
  fun getAll(): Deferred<Response<List<Coffee>>>

  @GET("coffees/user/{uid}")
  fun getByUid(@Path("uid") uid: String): Deferred<Response<Coffee>>

  @POST("coffee")
  fun save(@Body coffeeUser: CoffeeUser): Deferred<Response<Void>>

  companion object {
    const val GET_ALL = "coffees"
  }
}