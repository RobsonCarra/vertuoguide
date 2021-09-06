package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class Coffee(
    @PrimaryKey(autoGenerate = true) var id: Long? = 0L,
    var name: String,
    var capsules: Int,
    var description: String,
    var intensity: Int,
    var quantity: Int,
    var image: Int
) : Parcelable