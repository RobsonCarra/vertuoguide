package br.com.alura.ceep.ui.coffemachine.presentation.custom

import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CoffeeInterface {

    // code = identificar se deu certo ou nao
    // body = nossa resposta com os itens ou o item
    @GET("coffees")
    fun getAll(): Deferred<Response<List<Coffee>>>

    @GET("coffee/{uid}")
    fun getByUid(
        @Header("Authorization") authorization: String, @Path("uid") uid: String
    ): Deferred<Response<List<Coffee>>>

    @POST("coffee")
    fun save(
        @Header("Authorization") authorization: String,
        @Body coffee: Coffee
    ): Deferred<Response<Void>>

}