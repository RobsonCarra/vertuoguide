package br.com.alura.ceep.ui.coffemachine.presentation.view

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffes_table")
class Coffes {
    @PrimaryKey
    var uid = 0

    @ColumnInfo(name = "coffe_name")
    var coffeName: String? = null

    @ColumnInfo(name = "quantity_capsules")
    var quantityCapsules = 0
}