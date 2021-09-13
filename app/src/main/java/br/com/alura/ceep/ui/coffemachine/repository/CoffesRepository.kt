package br.com.alura.ceep.ui.coffemachine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeeInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CoffesRepository(private val coffesDao: CoffesDao) {

    val listCoffee = MutableLiveData<List<Coffee>>()

    fun getAll() {
        val retrofitClient = RetrofitConfig().getClient()
        val endpoint = retrofitClient.create(CoffeeInterface::class.java)
        val callback = endpoint.getAll()
        callback.enqueue(object : Callback<List<Coffee>> {
            override fun onResponse(
                call: Call<List<Coffee>>,
                response: Response<List<Coffee>>
            ) {
                response.body()?.let {
                    listCoffee.postValue(it)
                }
            }
            override fun onFailure(call: Call<List<Coffee>>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
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