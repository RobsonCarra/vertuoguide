package br.com.alura.ceep.ui.coffemachine.presentation.view;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coffes_table")
public class Coffes {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "coffe_name")
    public String coffeName;

    @ColumnInfo(name = "quantity_capsules")
    public int quantityCapsules;

    public String getCoffeName () {
        return coffeName;
    }

    public int getQuantityCapsules () {
        return quantityCapsules;
    }

}
