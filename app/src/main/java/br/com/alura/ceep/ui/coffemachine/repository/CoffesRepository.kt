package br.com.alura.ceep.ui.coffemachine.repository

import android.util.Log
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeeInterface
import kotlinx.coroutines.flow.flow
import java.net.HttpURLConnection

class CoffesRepository(private val coffesDao: CoffesDao) {

    suspend fun getAll() = flow {
        val client = RetrofitConfig().getClient()
        val api = client.create(CoffeeInterface::class.java)
        val req = api.getAll()
        val res = req.await()
        when (res.code()) {
            HttpURLConnection.HTTP_OK -> emit(res.body())
            else -> Log.e("Repositorio", "Erro ao buscar os dados do GetAll ")
        }
    }

    suspend fun getById() = flow {
        val client = RetrofitConfig().getClient()
        val api = client.create(CoffeeInterface::class.java)
        val req = api.getById()
        val res = req.await()
        when (res.code()) {
            HttpURLConnection.HTTP_CREATED -> emit(res.body())
            else -> Log.e("Repositorio", "Erro ao buscar os dados do GetById ")
        }
    }



//    fun getAll() = coffesDao.getAll()

//    fun getById(id: Long) = coffesDao.getById(id)
//
//    fun searchByName(name: String) {
//        val all = getAll()
////        val filtered = all.filter { it.name.lowercase().contains(name.lowercase()) }
////        return filtered
//    }
//
//    fun delete(coffee: Coffee): Boolean {
//        try {
//            coffesDao.delete(coffee)
//            return true
//        } catch (exception: Exception) {
//            return false
//        }
//    }
//
//    fun save(coffee: Coffee): Boolean {
//        val id = coffesDao.save(coffee)
//        return id > 0
//    }
}