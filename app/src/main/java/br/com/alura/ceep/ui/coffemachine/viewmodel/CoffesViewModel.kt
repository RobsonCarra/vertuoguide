package br.com.alura.ceep.ui.coffemachine.viewmodel

import androidx.lifecycle.LifecycleOwner
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
    val coffeeById = MutableLiveData<Coffee>()
    var coffeeFiltered = MutableLiveData<List<Coffee>>()
    val added = MutableLiveData<Boolean>(false)
//    val updated = MutableLiveData<Boolean>(true)
//    val deleted = MutableLiveData<Coffee>()
//    val filteredById = MutableLiveData<List<Coffee>>()

    fun getAll(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            coffesRepository.getAll().observe(lifecycleOwner) { result ->
                list.postValue(result)
            }
        }
    }

    fun searchByName(name: String, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            coffesRepository.getByName(name).observe(lifecycleOwner) { result ->
                coffeeFiltered.postValue(result)
            }
        }
    }

    fun searchByUid(uid: String, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            coffesRepository.getByUid(uid).observe(lifecycleOwner) { result ->
                coffeeById.postValue(result)
            }
        }
    }

    fun save(coffee: Coffee) {
        viewModelScope.launch(Dispatchers.IO) {
            val saved = coffesRepository.save(coffee)
            if (saved == saved) {
                added.postValue(true)
            }
        }
    }

//
//    fun getById() {
//        viewModelScope.launch {
//            coffesRepository.getById().collect { result ->
//                coffeeById.postValue(result)
//            }
//        }
//    }

//    fun searchByName(name: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val coffees = coffesRepository.searchByName(name)
//            coffeeFiltered.postValue(coffees)
//        }
//    }

//    fun getAll() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val coffes = coffesRepository.getAll()
//            list.postValue(coffes)
//        }
//    }

//    fun getById(id: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val coffes = coffesRepository.getById(id)
//            filteredById.postValue(coffes)
//        }
//    }
//
//    fun add(coffee: Coffee) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val saved = coffesRepository.save(coffee)
//            if (saved) {
//                added.postValue(true)
//            }
//        }
//    }
//
//    fun update(coffee: Coffee) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val update = coffesRepository.save(coffee)
//            if (update) {
//                updated.postValue(true)
//            }
//        }
//    }
//
//    fun delete(coffee: Coffee) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val excluded = coffesRepository.delete(coffee)
//            if (excluded) {
//                deleted.postValue(coffee)
//            }
//        }
//    }

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


