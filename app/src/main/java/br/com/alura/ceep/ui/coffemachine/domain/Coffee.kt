package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity @Parcelize
class Coffee(
  @PrimaryKey(autoGenerate = true) var id: Long,
  var name: String,
  var capsules: Long,
  var decription: String,
  var intensity: String,
  var quantity: String,
  var image: Int
) : Parcelable