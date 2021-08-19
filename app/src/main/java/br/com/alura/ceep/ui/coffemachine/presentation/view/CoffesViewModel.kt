package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CoffesViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: CoffesRepository
    private val mAllNames: LiveData<List<Coffes>>
    private val mAllCapsules: LiveData<List<Coffes>>
    fun getmAllNames(): LiveData<List<Coffes>> {
        return mAllNames
    }

    fun getmAllCapsules(): LiveData<List<Coffes>> {
        return mAllCapsules
    }

    init {
        mRepository = CoffesRepository(application)
        mAllNames = mRepository.allNames
        mAllCapsules = mRepository.allCapsules
    }
}