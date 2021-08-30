package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import org.parceler.Parcel
import java.io.Serializable

@Parcel
class CoffeMachineData : Serializable {
    var coffeType: String
    var coffeIntensity: String
    var coffeIntensityTitule: String
    var coffeDescription: String
    var coffeSize: String
    var coffeSizeTitule: String
    var coffeImage: Int


    constructor(
        coffeType: String, coffeIntensity: String, coffeIntensityTitule: String,
        coffeDescription: String, coffeSizeTitule: String, coffeSize: String, coffeImage: Int
    ) {
        this.coffeType = coffeType
        this.coffeIntensity = coffeIntensity
        this.coffeIntensityTitule = coffeIntensityTitule
        this.coffeDescription = coffeDescription
        this.coffeSizeTitule = coffeSizeTitule
        this.coffeSize = coffeSize
        this.coffeImage = coffeImage
    }

}