package br.com.alura.ceep.ui.coffemachine.presentation.view;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface CoffesDao {

    @Insert
    void insert (Coffes coffes);

    @Query("DELETE FROM coffes_table")
    void deleteAll ();

    @Query("SELECT * from coffes_table ORDER BY coffe_name ASC")
    LiveData<List<Coffes>> getAllNames ();

    @Query("SELECT * from coffes_table ORDER BY quantity_capsules ASC")
    LiveData<List<Coffes>> getAllCapsules ();

}
