package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Coffes.class}, version = 1, exportSchema = false)
public abstract class CoffeRoomDataBase extends RoomDatabase {

    public static CoffeRoomDataBase getDatabase (Application application) {
        return null;
    }

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

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback () {

            };

    public void onOpen (@NonNull SupportSQLiteDatabase db) {
        super.isOpen (db);
        new PopulateDbAsync (INSTANCE).execute ();
    }
};

private class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final CoffesDao mDao;
    String[] coffes = {"x", "y", "z"};

    PopulateDbAsync (CoffeRoomDataBase db) {
        mDao = db.coffesDao ();
    }

    @Override
    protected Void doInBackground (final Void... params) {
        // Start the app with a clean database every time.
        // Not needed if you only populate the database
        // when it is first created
        mDao.deleteAll ();

        for (int i = 0; i <= coffes.length - 1; i++) {
            Coffes coffes = new Coffes (coffes[i]);
            mDao.insert (coffes);
        }
        return null;
    }
}






