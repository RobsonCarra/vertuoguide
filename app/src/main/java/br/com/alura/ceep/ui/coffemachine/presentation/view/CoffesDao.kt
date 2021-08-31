package br.com.alura.ceep.ui.coffemachine.presentation.view

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface CoffesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(coffes: Coffes): Int

    @Delete
    fun deleteCoffe(coffes: Coffes): Int

    @Query("SELECT * from Coffes ORDER BY name ASC")
    fun getAllCoffes(): List<Coffes>

    @Query("SELECT* from Coffes WHERE id= :id ORDER BY name ASC")
    fun getById(id: Long): List<Coffes>


//    @Query("DELETE FROM coffes_table")
//    fun deleteAll()
//    @Query("SELECT * FROM User ORDER BY email ASC")
//    fun getAllUsers(): List<User>
//    @Query("SELECT * from Coffes ORDER BY name ASC")
//    val getAllCoffes(): List<Coffes>
//    @get:Query("SELECT * from coffes_table ORDER BY  ASC")
//    val getAllCapsules: LiveData<List<Coffes?>?>?

}