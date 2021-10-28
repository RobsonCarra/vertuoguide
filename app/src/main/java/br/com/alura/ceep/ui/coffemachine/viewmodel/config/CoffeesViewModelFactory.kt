package br.com.alura.ceep.ui.coffemachine.viewmodel.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.firebase.auth.FirebaseAuth

class CoffesViewModelFactory(
  private val coffesRepository: CoffesRepository,
  private val auth: FirebaseAuth,
  private val sharedPref: SharedPref
) :
  ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CoffesViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return CoffesViewModel(coffesRepository, auth, sharedPref) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}