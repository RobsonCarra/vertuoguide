package br.com.alura.ceep.ui.coffemachine.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.repository.CoffesDao

@Database(entities = [Coffee::class], version = 3, exportSchema = false)

abstract class CoffesRoomDataBase : RoomDatabase() {

    abstract fun coffesDao(): CoffesDao
    private class CoffesRoomDataBaseCallBack() : RoomDatabase.Callback() {
    }
    companion object {
        @Volatile
        private var INSTANCE: CoffesRoomDataBase? = null
        fun getDatabase(
            context: Context
        ): CoffesRoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoffesRoomDataBase::class.java,
                    "coffee2"
                ).addCallback(CoffesRoomDataBaseCallBack()).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
