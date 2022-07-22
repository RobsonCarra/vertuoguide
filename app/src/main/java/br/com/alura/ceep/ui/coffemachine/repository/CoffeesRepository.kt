package br.com.alura.ceep.ui.coffemachine.repository

import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.CoffeeUser
import br.com.alura.ceep.ui.coffemachine.domain.Experience
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.ConflictException
import br.com.alura.ceep.ui.coffemachine.exceptions.InternalException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.Res
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.net.HttpURLConnection

class CoffeesRepository(private val client: Retrofit) {

  // suspend fun searchByNameUser(name: String) = flow {
  //   val api = client.create(CoffeeInterface::class.java)
  //   val req = api.searchByNameUser(name)
  //   val res = req.await()
  //   when (res.code()) {
  //     HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body() as List<Coffee>))
  //     HttpURLConnection.HTTP_NOT_FOUND -> emit(Res.Failure(NotFoundException()))
  //     HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
  //     HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
  //     HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
  //     else -> emit(Res.Failure(Exception("Erro Generico")))
  //   }
  // }

  suspend fun searchByName(typed: String, boolean: Boolean) = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.searchByName(typed, boolean)
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body() as List<Coffee>))
      HttpURLConnection.HTTP_NOT_FOUND -> emit(Res.Failure(NotFoundException()))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      else -> emit(Res.Failure(Exception("Erro Generico")))
    }
  }

  suspend fun getAll() = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.getAll()
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body() as List<Coffee>))
      HttpURLConnection.HTTP_NOT_FOUND -> emit(Res.Failure(NotFoundException()))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      else -> emit(Res.Failure(Exception("Erro Generico")))
    }
  }

  suspend fun getByUid(uid: String) = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.getByUid(uid)
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body()))
      HttpURLConnection.HTTP_NOT_FOUND -> emit(Res.Failure(NotFoundException("Usuário não encontrado")))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      else -> emit(Res.Failure(Exception("Erro Generico")))
    }
  }

  suspend fun save(coffeeUser: CoffeeUser) = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.save(coffeeUser)
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(true))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      HttpURLConnection.HTTP_CONFLICT -> emit(Res.Failure(ConflictException()))
      HttpURLConnection.HTTP_INTERNAL_ERROR -> emit(Res.Failure(InternalException()))
      else -> emit(false)
    }
  }

  suspend fun saveExperience(experience: Experience) = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.saveExperience(experience)
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(true))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      HttpURLConnection.HTTP_CONFLICT -> emit(Res.Failure(ConflictException()))
      HttpURLConnection.HTTP_INTERNAL_ERROR -> emit(Res.Failure(InternalException()))
    }
  }
    suspend fun getExperiences() = flow {
      val api = client.create(CoffeeInterface::class.java)
      val req = api.getAllExperiences()
      val res = req.await()
      when (res.code()) {
        HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body() as List<Experience>))
        HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
        HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
        HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
        HttpURLConnection.HTTP_CONFLICT -> emit(Res.Failure(ConflictException()))
        HttpURLConnection.HTTP_INTERNAL_ERROR -> emit(Res.Failure(InternalException()))
      }
    }

  suspend fun getByUser() = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.getByUser()
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body()))
      HttpURLConnection.HTTP_NOT_FOUND -> emit(Res.Failure(NotFoundException("Usuário não encontrado")))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      else -> emit(Res.Failure(Exception("Erro Generico")))
    }
  }
}


