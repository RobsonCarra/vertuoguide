package br.com.alura.ceep.ui.coffemachine.presentation.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoffesViewModel extends AndroidViewModel {
    private CoffesRepository mRepository;
    private LiveData<List<Coffes>> mAllNames;
    private LiveData<List<Coffes>> mAllCapsules;

    public CoffesViewModel (@NonNull @NotNull Application application) {
        super (application);
        mRepository = new CoffesRepository (application);
        mAllNames = mRepository.getAllNames ();
        mAllCapsules = mRepository.getAllCapsules ();
    }

    LiveData<List<Coffes>> getmAllNames () {
        return mAllNames;
    }

    LiveData<List<Coffes>> getmAllCapsules () {
        return mAllCapsules;
    }
}

