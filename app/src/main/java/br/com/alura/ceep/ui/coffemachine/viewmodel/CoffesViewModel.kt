package br.com.alura.ceep.ui.coffemachine.viewmodel

import androidx.lifecycle.*
import androidx.room.RoomDatabase
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.repository.CoffesDao
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CoffesViewModel(

    private val coffesRepository: CoffesRepository

) : ViewModel() {
    val list = MutableLiveData<List<Coffee>>()
    var coffeeFiltered: MutableLiveData<List<Coffee>> = MutableLiveData()
    val filteredById = MutableLiveData<List<Coffee>>()
    val added = MutableLiveData<Boolean>(false)
    val updated = MutableLiveData<Boolean>(true)
    val deleted = MutableLiveData<Coffee>()


    fun searchByName(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val coffees = coffesRepository.searchByName(name)
            coffeeFiltered.postValue(coffees)
        }
    }

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val coffes = coffesRepository.getAll()
            list.postValue(coffes)
        }
    }

    fun getById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val coffes = coffesRepository.getById(id)
            filteredById.postValue(coffes)
        }
    }

    fun add(coffee: Coffee) {
        viewModelScope.launch(Dispatchers.IO) {
            val saved = coffesRepository.save(coffee)
            if (saved) {
                added.postValue(true)
            }
        }
    }

    fun update(coffee: Coffee) {
        viewModelScope.launch(Dispatchers.IO) {
            val update = coffesRepository.save(coffee)
            if (update) {
                updated.postValue(true)
            }
        }
    }

    fun delete(coffee: Coffee) {
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
