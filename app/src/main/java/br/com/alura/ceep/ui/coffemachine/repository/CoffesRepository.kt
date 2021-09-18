package br.com.alura.ceep.ui.coffemachine.repository

import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class CoffesRepository(private val coffesDao: CoffesDao) {

  fun getAll(): MutableLiveData<List<Coffee>> {
    val data = MutableLiveData<List<Coffee>>()
    val coffeeList = ArrayList<Coffee>()
    val db = FirebaseFirestore.getInstance()
    db.collection("coffees")
      .addSnapshotListener { snapshot, exception ->
        snapshot?.documents?.let { docs ->
          docs.forEach { doc ->
            val type = object : TypeToken<Coffee>() {}.type
            val json = Gson().toJson(doc.data)
            val coffee = Gson().fromJson<Coffee>(json, type)
            coffee?.let { cf ->
              coffeeList.add(cf)
            }
          }
          data.postValue(coffeeList)
        }
      }
    return data
  }

  fun getByName(name: String): MutableLiveData<Coffee> {
    val data = MutableLiveData<Coffee>()
    val db = FirebaseFirestore.getInstance()
    db.collection("coffees").whereEqualTo("name", name)
      .addSnapshotListener { snapshot, exception ->
        snapshot?.documents?.let { docs ->
          docs.forEach { doc ->
            val type = object : TypeToken<Coffee>() {}.type
            val json = Gson().toJson(doc.data)
            val coffee = Gson().fromJson<Coffee>(json, type)
            coffee?.let { cf ->
              data.postValue(cf)
            }
          }
        }
      }
    return data
  }

  fun getByUid(uid: String): MutableLiveData<Coffee> {
    val data = MutableLiveData<Coffee>()
    val db = FirebaseFirestore.getInstance()
    db.collection("coffees").whereEqualTo("uid", uid)
      .addSnapshotListener { snapshot, exception ->
        snapshot?.documents?.let { docs ->
          docs.forEach { doc ->
            val type = object : TypeToken<Coffee>() {}.type
            val json = Gson().toJson(doc.data)
            val coffee = Gson().fromJson<Coffee>(json, type)
            coffee?.let { cf ->
              data.postValue(cf)
            }
          }
        }
      }
    return data
  }

  fun save(coffee: Coffee) {
    val db = FirebaseFirestore.getInstance()
    db.collection("coffees").document().set(coffee)
  }
}

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

//
//suspend fun getAll() = flow {
//    val client = RetrofitConfig().getClient()
//    val api = client.create(CoffeeInterface::class.java)
//    val req = api.getAll()
//    val res = req.await()
//    when (res.code()) {
//        HttpURLConnection.HTTP_OK -> emit(res.body())
//        else -> Log.e("Repositorio", "Erro ao buscar os dados do GetAll ")
//    }
//}
////
//    suspend fun getById() = flow {
//        val client = RetrofitConfig().getClient()
//        val api = client.create(CoffeeInterface::class.java)
//        val req = api.getById()
//        val res = req.await()
//        when (res.code()) {
//            HttpURLConnection.HTTP_CREATED -> emit(res.body())
//            else -> Log.e("Repositorio", "Erro ao buscar os dados do GetById ")
//        }
//    }
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


