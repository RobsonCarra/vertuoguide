package br.com.alura.ceep.ui.coffemachine.repository

import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.exceptions.BadGatewayException
import br.com.alura.ceep.ui.coffemachine.exceptions.BadRequestException
import br.com.alura.ceep.ui.coffemachine.exceptions.ConflictException
import br.com.alura.ceep.ui.coffemachine.exceptions.InternalException
import br.com.alura.ceep.ui.coffemachine.exceptions.NoContentException
import br.com.alura.ceep.ui.coffemachine.exceptions.NotFoundException
import br.com.alura.ceep.ui.coffemachine.helpers.Res
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeeInterface
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.lang.Exception
import java.net.HttpURLConnection

class CoffesRepository(private val coffesDao: CoffesDao, private val client: Retrofit) {

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
      else ->  emit(Res.Failure(Exception("Erro Generico")))
    }
    // when (res.code()) {
    //   HttpURLConnection.HTTP_OK -> emit(res.body())
    //   HttpURLConnection.HTTP_NOT_FOUND -> emit("Usuario nao encontrado")
    //   HttpURLConnection.HTTP_BAD_REQUEST -> emit("Erro de request")
    //   HttpURLConnection.HTTP_BAD_GATEWAY -> emit("Erro de servidor")
    //   HttpURLConnection.HTTP_NO_CONTENT -> emit("Dados nao encontrados")
    //   else -> emit("Erro Generico")
    // }
  }

  suspend fun getByUid(uid: String) = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.getByUid(uid)
    val res = req.await()
    when (res.code()) {
      HttpURLConnection.HTTP_OK -> emit(Res.Success(res.body()?.first()))
      HttpURLConnection.HTTP_NOT_FOUND -> emit(Res.Failure(NotFoundException("Usuário não encontrado")))
      HttpURLConnection.HTTP_BAD_REQUEST -> emit(Res.Failure(BadRequestException()))
      HttpURLConnection.HTTP_BAD_GATEWAY -> emit(Res.Failure(BadGatewayException()))
      HttpURLConnection.HTTP_NO_CONTENT -> emit(Res.Failure(NoContentException()))
      else ->  emit(Res.Failure(Exception("Erro Generico")))
    }
  }

  suspend fun save(coffee: Coffee) = flow {
    val api = client.create(CoffeeInterface::class.java)
    val req = api.save(coffee)
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
//    fun getAll() = coffesDao.getAll()
//    fun getById(id: Long) = coffesDao.getById(id)
//    fun searchByName(name: String) {
//        val all = getAll()
////        val filtered = all.filter { it.name.lowercase().contains(name.lowercase()) }
////        return filtered
//    }
//    fun delete(coffee: Coffee): Boolean {
//        try {
//            coffesDao.delete(coffee)
//            return true
//        } catch (exception: Exception) {
//            return false
//        }
//    }
//    fun save(coffee: Coffee): Boolean {
//        val id = coffesDao.save(coffee)
//        return id > 0
}


