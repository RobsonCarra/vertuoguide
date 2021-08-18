package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Coffes.class}, version = 1, exportSchema = false)
public abstract class CoffeRoomDataBase extends RoomDatabase {

    public abstract CoffesDao coffesDao ();

    private static CoffeRoomDataBase INSTANCE;

    static CoffeRoomDataBase CoffeRoomDataBase (final Context context) {
        if (INSTANCE == null) {
            synchronized (CoffeRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder (context.getApplicationContext (),
                            CoffeRoomDataBase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration ()
                            .build ();
                }
            }
        }
        return INSTANCE;
    }
}

