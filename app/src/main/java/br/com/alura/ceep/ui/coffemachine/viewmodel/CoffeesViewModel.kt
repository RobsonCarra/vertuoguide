package br.com.alura.ceep.ui.coffemachine.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.domain.CoffeeUser
import br.com.alura.ceep.ui.coffemachine.helpers.Res
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoffeesViewModel(
  private val coffeesRepository: CoffeesRepository,
  private val auth: FirebaseAuth,
  private val sharedPref: SharedPref
) : ViewModel() {

  // login
  val showLoader = MutableLiveData<Boolean>()
  val goToHome = MutableLiveData<Boolean>()
  val showError = MutableLiveData<Int>()
  val list = MutableLiveData<List<Coffee>>()
  val listByUser = MutableLiveData<List<Coffee>>()
  val coffeeById = MutableLiveData<Coffee>()
  var coffeeFiltered = MutableLiveData<List<Coffee>>()
  val added = MutableLiveData(false)
  val error = MutableLiveData<Exception>()
  val errorById = MutableLiveData<Exception>()
  val errorByUser = MutableLiveData<Exception>()
  val errorSave = MutableLiveData<Exception>()

  fun getAll() {
    viewModelScope.launch {
      coffeesRepository.getAll().collect { result ->
        when (result) {
          is Res.Success -> list.postValue(result.items as List<Coffee>)
          is Res.Failure -> error.postValue(result.exception)
        }
      }
    }
  }

  fun searchByName(name: String, lifecycleOwner: LifecycleOwner) {
    viewModelScope.launch {
      sharedPref.getString(SharedPref.UID)?.let { uid ->
        // coffeesRepository.getByName(uid, name).observe(lifecycleOwner) { result ->
        //   coffeeFiltered.postValue(result)
        // }
      }
    }
  }

  fun searchByUid(uid: String) {
    viewModelScope.launch {
      coffeesRepository.getByUid(uid).collect { result ->
        when (result) {
          is Res.Success -> coffeeById.postValue(result.items as Coffee)
          is Res.Failure -> errorById.postValue(result.exception)
        }
      }
    }
  }

  fun searchByUser() {
    viewModelScope.launch {
      coffeesRepository.getByUser().collect { result ->
        when (result) {
          is Res.Success -> listByUser.postValue(result.items as List<Coffee>)
          is Res.Failure -> errorByUser.postValue(result.exception)
        }
      }
    }
  }

  fun save(coffeeUser: CoffeeUser) {
    viewModelScope.launch {
      coffeesRepository.save(coffeeUser).collect { saved ->
        when (saved) {
          is Res.Success -> added.postValue(saved.items as Boolean)
          is Res.Failure -> errorSave.postValue(saved.exception)
        }
      }
    }
  }

  fun signIn(email: String, password: String) {
    showLoader.postValue(true)
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        task.getResult()?.user?.uid?.let { uid -> sharedPref.put(SharedPref.UID, uid) }
        auth.currentUser?.getIdToken(true)?.addOnCompleteListener { result ->
          if (result.isSuccessful) {
            result.result?.token?.let { token ->
              sharedPref.put(SharedPref.TOKEN, token)
              showLoader.postValue(false)
              goToHome.postValue(true)
            } ?: unauthorized()
          } else {
            unauthorized()
          }
        }
      } else {
        unauthorized()
      }
    }
  }

  private fun unauthorized() {
    showLoader.postValue(false)
    showError.postValue(R.string.unauthorized)
  }
}


