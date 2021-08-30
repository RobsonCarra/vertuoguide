package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import org.parceler.Parcel
import java.io.Serializable

@Parcel
class CoffeMachineDataItem : Serializable {
    var coffeType: String?
    var coffeImage: Int? = null


    constructor(
        coffeType: String?, coffeIntensity: String?,
        coffeImage: Int?
    ) {
        this.coffeType = coffeType
        this.coffeImage = coffeImage
    }

}