package br.com.alura.ceep.ui.coffemachine

import android.app.Application
import androidx.multidex.MultiDex

class CoffesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}

