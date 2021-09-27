package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity
@Parcelize
class Coffee(
    @PrimaryKey var id: Long? = null,
    var name: String,
    var capsules: Int,
    var description: String,
    var intensity: String,
    var quantity: String,
    var uid: String = UUID.randomUUID().toString(),
    var image: String = "${uid}.jpg",
) : Parcelable