package br.com.alura.ceep.ui.coffemachine.presentation.view

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

interface CoffesDao {
    @Insert
    fun insert(coffes: Coffes?)

    @Query("DELETE FROM coffes_table")
    fun deleteAll()

    @get:Query("SELECT * from coffes_table ORDER BY coffe_name ASC")
    val allNames: LiveData<List<Coffes?>?>?

    @get:Query("SELECT * from coffes_table ORDER BY quantity_capsules ASC")
    val allCapsules: LiveData<List<Coffes?>?>?
}