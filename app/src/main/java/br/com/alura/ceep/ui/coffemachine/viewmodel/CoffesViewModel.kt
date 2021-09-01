package br.com.alura.ceep.ui.coffemachine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoffesViewModel(

    private val coffesRepository: CoffesRepository

) : ViewModel() {
    val list = MutableLiveData<List<Coffee>>()
    val filteredById = MutableLiveData<List<Coffee>>()
    val added = MutableLiveData<Boolean>(false)
    val updated = MutableLiveData<Boolean>(true)
    val deleted = MutableLiveData<Coffee>()

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

    fun addCoffe(coffee: Coffee) {
        viewModelScope.launch(Dispatchers.IO) {
            val saved = coffesRepository.save(coffee)
            if (saved) {
                added.postValue(true)
            }
        }
    }

    fun updateCoffe(coffee: Coffee) {
        viewModelScope.launch(Dispatchers.IO) {
            val update = coffesRepository.save(coffee)
            if (update) {
                updated.postValue(true)
            }
        }
    }

    fun deleteCoffe(coffee: Coffee) {
        viewModelScope.launch(Dispatchers.IO) {
            val excluded = coffesRepository.delete(coffee)
            if (excluded) {
                deleted.postValue(coffee)
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