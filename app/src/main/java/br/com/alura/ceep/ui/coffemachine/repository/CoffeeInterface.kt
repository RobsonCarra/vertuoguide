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

  @GET(GET_BY_UID)
  fun getByUid(@Path(UID) uid: String): Deferred<Response<Coffee>>

  @GET(GET_BY_USER)
  fun getByUser(): Deferred<Response<List<Coffee>>>

  @GET(SEARCH_BY_NAME_USER)
  fun searchByNameUser(@Path(NAME) name: String): Deferred<Response<List<Coffee>>>

  @GET(SEARCH_BY_NAME)
  fun searchByName(@Path(NAME) name: String): Deferred<Response<List<Coffee>>>

  @POST(SAVE)
  fun save(@Body coffeeUser: CoffeeUser): Deferred<Response<Void>>

  companion object {
    const val GET_ALL = "coffees"
    const val GET_BY_UID = "coffees/user/{uid}"
    const val GET_BY_USER = "coffees/user"
    const val SEARCH_BY_NAME_USER = "coffees/search/{name}"
    const val SEARCH_BY_NAME = "coffees/{name}"
    const val SAVE = "coffee"
    const val UID = "uid"
    const val NAME = "name"
  }
}