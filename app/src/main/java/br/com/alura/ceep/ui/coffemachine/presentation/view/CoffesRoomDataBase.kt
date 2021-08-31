package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.ceep.ui.coffemachine.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Coffes::class], version = 2, exportSchema = false)

abstract class CoffesRoomDataBase : RoomDatabase() {

    abstract fun coffesDao(): CoffesDao

    private class CoffesRoomDataBaseCallBack() : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                GlobalScope.launch(Dispatchers.IO) {
                    val coffesDao = database.coffesDao()
                    database.clearAllTables()
                    coffesDao.save(Coffes(1,
                        "Ristretto",
                        0,
                        "É preparado de forma semelhante ao espresso," +
                            " mas com metade da água e, embora a quantidade de café seja a mesma," +
                            " uma moagem mais fina é usada para retardar sua extração." +
                            " A extração é geralmente interrompida por volta dos 15 segundos," +
                            " em vez dos 25 a 30 segundos do espresso.",
                        "240 ml",
                        "10",
                        R.drawable.ristretto
                    ))
                    coffesDao.save(
                        Coffes(2,
                            "Capuccino",
                            0,
                            "Cappuccino, pronunciado capuchino," +
                                " é uma bebida italiana preparada com café expresso e leite." +
                                " Um cappuccino clássico, muito famoso no Brasil" +
                                " e consiste em um terço de café expresso," +
                                " um terço de leite vaporizado e um terço de espuma de leite vaporizado.",
                            "240 ml",
                            "6",
                            R.drawable.capuccino)
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
                    "User_db1"
                ).addCallback(CoffesRoomDataBaseCallBack()).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
