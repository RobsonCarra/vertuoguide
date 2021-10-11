package br.com.alura.ceep.ui.coffemachine.presentation.custom

import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeInterface {

    // code = identificar se deu certo ou nao
    // body = nossa resposta com os itens ou o item
    @GET("coffees")
    fun getAll(): Deferred<Response<List<Coffee>>>

    @GET("coffees/{uid}")
    fun getByUid(
        @Path("uid") uid: String
    ): Deferred<Response<Coffee>>
}