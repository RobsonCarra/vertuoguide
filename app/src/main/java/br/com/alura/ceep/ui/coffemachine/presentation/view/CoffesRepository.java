package br.com.alura.ceep.ui.coffemachine.presentation.view;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CoffesRepository {
    private CoffesDao mnamesDao;
    private CoffesDao mquantityDao;
    private LiveData<List<Coffes>> mAllNames;
    private LiveData<List<Coffes>>mAllCapsules;
}
