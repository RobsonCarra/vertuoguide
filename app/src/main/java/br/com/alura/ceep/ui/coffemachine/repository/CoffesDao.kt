package br.com.alura.ceep.ui.coffemachine.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

interface CoffesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(coffee: Coffee): Int

    @Delete
    fun deleteCoffe(coffee: Coffee): Int

    @Query("SELECT * from Coffee ORDER BY name ASC")
    fun getAllCoffes(): List<Coffee>

    @Query("SELECT* from Coffee WHERE id= :id ORDER BY name ASC")
    fun getById(id: Long): List<Coffee>


//    @Query("DELETE FROM coffes_table")
//    fun deleteAll()
//    @Query("SELECT * FROM User ORDER BY email ASC")
//    fun getAllUsers(): List<User>
//    @Query("SELECT * from Coffes ORDER BY name ASC")
//    val getAllCoffes(): List<Coffes>
//    @get:Query("SELECT * from coffes_table ORDER BY  ASC")
//    val getAllCapsules: LiveData<List<Coffes?>?>?

}