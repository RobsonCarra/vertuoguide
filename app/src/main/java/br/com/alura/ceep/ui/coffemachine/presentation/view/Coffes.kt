package br.com.alura.ceep.ui.coffemachine.presentation.view

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "coffes_table")
@Entity
class Coffes (
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var capsules: Long,
    var decription: String,
    var intensity: String,
    var quantity: String,
    var image: Int
)
//    @PrimaryKey var uid = 0
//    @ColumnInfo(name = "coffe_name")
//    var coffeName: String? = null
//
//    @ColumnInfo(name = "quantity_capsules")
//    var quantityCapsules = 0
