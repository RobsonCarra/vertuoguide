package br.com.alura.ceep.ui.coffemachine.viewmodel.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffeesViewModel
import com.google.firebase.auth.FirebaseAuth

class CoffesViewModelFactory(
  private val coffeesRepository: CoffeesRepository,
  private val auth: FirebaseAuth,
  private val sharedPref: SharedPref
) :
  ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CoffeesViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return CoffeesViewModel(coffeesRepository, auth, sharedPref) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}