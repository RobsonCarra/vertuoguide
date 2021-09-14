package br.com.alura.ceep.ui.coffemachine.presentation.custom

import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CoffeeInterface {

    // code = identificar se deu certo ou nao
    // body = nossa resposta com os itens ou o item
    @GET("2f832f7a-7788-4236-8634-a7f62aa5595c")
    fun getAll(): Deferred<Response<List<Coffee>>>

    @GET("443d670e-0c54-47f4-bcbc-84509998e283")
    fun getById(): Deferred<Response<Coffee>>
}