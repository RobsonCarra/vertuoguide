package br.com.alura.ceep.ui.coffemachine.presentation.view

import androidx.annotation.WorkerThread
import java.util.concurrent.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class CoffesRepository(private val CoffesDao: CoffesDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allCaps: Flow<List<Coffes>> = CoffesDao.allCapsules();
    val allNames: Flow<List<Coffes>> = CoffesDao.allNames();

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(coffes: Coffes) {
        CoffesDao.insert(coffes)
    }
}