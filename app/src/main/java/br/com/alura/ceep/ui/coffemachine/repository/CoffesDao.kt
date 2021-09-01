package br.com.alura.ceep.ui.coffemachine.repository

import androidx.room.*
import br.com.alura.ceep.ui.coffemachine.domain.Coffee

@Dao
interface CoffesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(coffee: Coffee): Int

    @Delete
    fun delete(coffee: Coffee): Int

    @Query("SELECT * from Coffee ORDER BY name ASC")
    fun getAll(): List<Coffee>

    @Query("SELECT* from Coffee WHERE id= :id ORDER BY name ASC")
    fun getById(id: Long): List<Coffee>

}