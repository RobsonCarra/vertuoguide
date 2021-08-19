package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class CoffesRepository internal constructor(application: Application?) {
    private val mnamesDao: CoffesDao
    private val mquantityDao: CoffesDao
    val allNames: LiveData<List<Coffes>>
    val allCapsules: LiveData<List<Coffes>>

    fun insert(coffes: Coffes?) {
        insertAsyncTask(mnamesDao).execute(coffes)
    }

    fun insert(coffes: Coffes?) {
        insertAsyncTask(mquantityDao).execute(coffes)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: CoffesDao) :
        AsyncTask<Coffes?, Void?, Void?>() {
        fun execute(coffes: Coffes?) {}
        protected override fun doInBackground(vararg params: Coffes): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    init {
        val db = CoffeRoomDataBase.getDatabase(application)
        mnamesDao = db.coffesDao()
        mquantityDao = db.coffesDao()
        allNames = mnamesDao.allNames
        allCapsules = mquantityDao.allCapsules
    }
}