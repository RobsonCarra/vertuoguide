package br.com.alura.ceep.ui.coffemachine.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.repository.CoffeesDao

@Database(entities = [Coffee::class], version = 3, exportSchema = false)

abstract class CoffeesRoomDataBase : RoomDatabase() {

    abstract fun coffesDao(): CoffeesDao
    private class CoffesRoomDataBaseCallBack() : RoomDatabase.Callback() {
    }
    companion object {
        @Volatile
        private var INSTANCE: CoffeesRoomDataBase? = null
        fun getDatabase(
            context: Context
        ): CoffeesRoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoffeesRoomDataBase::class.java,
                    "coffee2"
                ).addCallback(CoffesRoomDataBaseCallBack()).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
