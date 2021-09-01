package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.app.Application

class CoffesApplication : Application() {

     val database by lazy {
        CoffesRoomDataBase.getDatabase(this)
    }
    val coffesRepository by lazy {
        CoffesRepository(database.coffesDao())
    }
}

