package br.com.alura.ceep.ui.coffemachine

import android.app.Application
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository

class CoffesApplication : Application() {

     val database by lazy {
       CoffesRoomDataBase.getDatabase(this)
    }
    val coffesRepository by lazy {
        CoffesRepository(database.coffesDao())
    }
}

