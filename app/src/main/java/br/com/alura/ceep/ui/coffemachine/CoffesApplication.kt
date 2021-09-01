package br.com.alura.ceep.ui.coffemachine

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository

class CoffesApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    MultiDex.install(this)
  }

  companion object {
    fun repository(context: Context): CoffesRepository {
      return CoffesRepository(
        CoffesRoomDataBase.getDatabase(context).coffesDao()
      )
    }
  }
}

