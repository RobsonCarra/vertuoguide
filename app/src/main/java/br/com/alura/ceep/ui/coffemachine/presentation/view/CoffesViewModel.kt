package br.com.alura.ceep.ui.coffemachine.presentation.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffesViewModel(

    private val coffesRepository: CoffesRepository

) : ViewModel() {
    val list = MutableLiveData<List<Coffes>>()
    val filteredById = MutableLiveData<List<Coffes>>()
    val added = MutableLiveData<Boolean>(false)
    val updated = MutableLiveData<Boolean>(true)
    val deleted = MutableLiveData<Coffes>()

    fun getAllCoffes() {
        viewModelScope.launch(Dispatchers.IO) {
            val coffes = coffesRepository.get()
            list.postValue(coffes)
        }
    }

    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val coffes = coffesRepository.getSpecicCoffe(id)
            filteredById.postValue(coffes)
        }
    }

    fun addCoffe(coffes: Coffes) {
        viewModelScope.launch(Dispatchers.IO) {
            val saved = coffesRepository.save(coffes)
            if (saved) {
                added.postValue(true)
            }
        }
    }

    fun updateCoffe(coffes: Coffes) {
        viewModelScope.launch(Dispatchers.IO) {
            val update = coffesRepository.save(coffes)
            if (update) {
                updated.postValue(true)
            }
        }
    }

    fun deleteCoffe(coffes: Coffes) {
        viewModelScope.launch(Dispatchers.IO) {
            val excluded = coffesRepository.delete(coffes)
            if (excluded) {
                deleted.postValue(coffes)
            }
        }
    }

    class CoffesViewModelFactory(
        private val coffesRepository: CoffesRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CoffesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CoffesViewModel(coffesRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}