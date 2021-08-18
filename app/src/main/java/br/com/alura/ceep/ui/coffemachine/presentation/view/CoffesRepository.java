package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CoffesRepository {
    private CoffesDao mnamesDao;
    private CoffesDao mquantityDao;
    private LiveData<List<Coffes>> mAllNames;
    private LiveData<List<Coffes>> mAllCapsules;

    CoffesRepository (Application application) {
        CoffeRoomDataBase db = CoffeRoomDataBase.getDatabase (application);
        mnamesDao = db.coffesDao ();
        mquantityDao = db.coffesDao ();
        mAllNames = mnamesDao.getAllNames ();
        mAllCapsules = mquantityDao.getAllCapsules ();
    }

    LiveData<List<Coffes>> getAllNames () {
        return mAllNames;
    }

    LiveData<List<Coffes>> getAllCapsules () {
        return mAllCapsules;
    }

    public void insert (Coffes coffes) {
        new insertAsyncTask (mnamesDao).execute (coffes);
    }

    public void insert (Coffes coffes) {
        new insertAsyncTask (mquantityDao).execute (coffes);
    }


    private static class insertAsyncTask extends AsyncTask <Coffes, Void, Void>{
        private CoffesDao mAsyncTaskDao;

        insertAsyncTask(CoffesDao dao) {
            mAsyncTaskDao = dao;
        }
        public void execute (Coffes coffes) {
        }
        @Override
        protected Void doInBackground(final Coffes... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
