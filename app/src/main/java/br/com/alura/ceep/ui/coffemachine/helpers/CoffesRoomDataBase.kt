package br.com.alura.ceep.ui.coffemachine.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.ceep.ui.coffemachine.R.drawable
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.repository.CoffesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Coffee::class], version = 2, exportSchema = false)

abstract class CoffesRoomDataBase : RoomDatabase() {

    abstract fun coffesDao(): CoffesDao
    private class CoffesRoomDataBaseCallBack() : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                GlobalScope.launch(Dispatchers.IO) {
                    val coffesDao = database.coffesDao()
                    database.clearAllTables()
                    coffesDao.save(
                        Coffee(
                            11,
                            "Cabloco",
                            0,
                            "xx",
                            "10",
                            "1",
                            drawable.capuccino
                        )
                    )
                }
            }
        }
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
                    "Coffe_db"
                ).addCallback(CoffesRoomDataBaseCallBack()).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
