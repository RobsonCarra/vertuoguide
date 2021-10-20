package br.com.alura.ceep.ui.coffemachine.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.RetrofitConfig
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeeInterface
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import java.net.HttpURLConnection

class CoffesRepository(private val coffesDao: CoffesDao) {

    //    fun getAll(): MutableLiveData<List<Coffee>> {
//        val data = MutableLiveData<List<Coffee>>()
//        val coffeeList = ArrayList<Coffee>()
//        val db = FirebaseFirestore.getInstance()
//        db.collection("coffees")
//            .addSnapshotListener { snapshot, exception ->
//                snapshot?.documents?.let { docs ->
//                    docs.forEach { doc ->
//                        val type = object : TypeToken<Coffee>() {}.type
//                        val json = Gson().toJson(doc.data)
//                        val coffee = Gson().fromJson<Coffee>(json, type)
//                        coffee?.let { cf ->
//                            coffeeList.add(cf)
//                        }
//                    }
//                    data.postValue(coffeeList)
//                }
//            }
//        return data
//    }
//
    fun getToken(token: String) {
    }

    fun getByName(name: String): MutableLiveData<List<Coffee>> {
        val datas = MutableLiveData<List<Coffee>>()
        val list = ArrayList<Coffee>()
        val db = FirebaseFirestore.getInstance()
        db.collection("coffees")
            .addSnapshotListener { snapshot, exception ->
                snapshot?.documents?.let { docs ->
                    docs.forEach { doc ->
                        val type = object : TypeToken<Coffee>() {}.type
                        val json = Gson().toJson(doc.data)
                        val coffee = Gson().fromJson<Coffee>(json, type)
                        coffee?.let { cf ->
                            list.add(cf)
                        }
                    }
                    val filtered =
                        list.filter { coffee -> coffee.name.lowercase().contains(name.lowercase()) }
                    datas.postValue(filtered)
                }
            }
        return datas
    }
//
//    fun getByUid(uid: String): MutableLiveData<Coffee> {
//        val data = MutableLiveData<Coffee>()
//        val db = FirebaseFirestore.getInstance()
//        db.collection("coffees").whereEqualTo("uid", uid)
//            .addSnapshotListener { snapshot, exception ->
//                snapshot?.documents?.let { docs ->
//                    docs.forEach { doc ->
//                        val type = object : TypeToken<Coffee>() {}.type
//                        val json = Gson().toJson(doc.data)
//                        val coffee = Gson().fromJson<Coffee>(json, type)
//                        coffee?.let { cf ->
//                            data.postValue(cf)
//                        }
//                    }
//                }
//            }
//        return data
//    }
//
//    fun save(coffee: Coffee) {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("coffees").document(coffee.uid).set(coffee)
//    }


//        fun getAll() = flow {
//            val db = FirebaseFirestore.getInstance()
//            db.collection("coffees")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        val myObjects = result.toObjects(Coffee::class.java)
//                        emit(myObjects)
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
//                }
//        }

    suspend fun getAll(token: String) = flow {
        val client = RetrofitConfig().getClient()
        val api = client.create(CoffeeInterface::class.java)
        val req = api.getAll(token)
        val res = req.await()
        when (res.code()) {
            HttpURLConnection.HTTP_OK -> emit(res.body())
            else -> Log.e("Repositorio", "Erro ao buscar os dados do GetAll ")
        }
    }

    suspend fun getByUid(uid: String, token: String) = flow {
        val client = RetrofitConfig().getClient()
        val api = client.create(CoffeeInterface::class.java)
        val req = api.getByUid(token, uid)
        val res = req.await()
        when (res.code()) {
            HttpURLConnection.HTTP_OK -> emit(res.body()?.first())
            else -> Log.e("Repositorio", "Erro ao buscar os dados do GetById ")
        }
    }

    suspend fun save(coffee: Coffee, token: String) = flow {
        val client = RetrofitConfig().getClient()
        val api = client.create(CoffeeInterface::class.java)
        val req = api.save(token, coffee)
        val res = req.await()
        when (res.code()) {
            HttpURLConnection.HTTP_CREATED -> emit(true)
            else -> emit(false)
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
}


