package br.com.alura.ceep.ui.coffemachine.repository

import br.com.alura.ceep.ui.coffemachine.domain.Coffee


class CoffesRepository(private val coffesDao: CoffesDao) {

    fun getAll() = coffesDao.getAll()

    fun getById(id: Long) = coffesDao.getById(id)

    fun getByName(name: String) = coffesDao.getByName(name)

    fun delete(coffee: Coffee): Boolean {
        try {
            coffesDao.delete(coffee)
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    fun save(coffee: Coffee): Boolean {
        val id = coffesDao.save(coffee)
        return id > 0
    }
}