package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import org.parceler.Parcel
import java.io.Serializable

@Parcel
class CoffeMachineData : Serializable {
    var coffeType: String?
    var coffeIntensity: String?
    var coffeIntensityTitule: String?
    var coffeDescription: String?
    var coffeSize: String?
    var coffeSizeTitule: String?

    constructor(
        coffeType: String?, coffeIntensity: String?, coffeIntensityTitule: String?,
        coffeDescription: String?, coffeSizeTitule: String?, coffeSize: String?, coffeImage: Int?
    ) {
        this.coffeType = coffeType
        this.coffeIntensity = coffeIntensity
        this.coffeIntensityTitule = coffeIntensityTitule
        this.coffeDescription = coffeDescription
        this.coffeSizeTitule = coffeSizeTitule
        this.coffeSize = coffeSize
        this.coffeImage = coffeImage
    }

    protected constructor(`in`: android.os.Parcel) {
        coffeType = `in`.readString()
        coffeIntensity = `in`.readString()
        coffeIntensityTitule = `in`.readString()
        coffeDescription = `in`.readString()
        coffeSize = `in`.readString()
        coffeSizeTitule = `in`.readString()
        if (`in`.readByte().toInt() == 0) {
            coffeImage = null
        } else {
            coffeImage = `in`.readInt()
        }
    }

    fun describeContents(): Int {
        return 0
    }

    var coffeImage: Int? = null

    companion object {
        val CREATOR: Parcelable.Creator<CoffeMachineData> =
            object : Parcelable.Creator<CoffeMachineData?> {
                override fun createFromParcel(`in`: android.os.Parcel): CoffeMachineData? {
                    return CoffeMachineData(`in`)
                }

                override fun newArray(size: Int): Array<CoffeMachineData?> {
                    return arrayOfNulls(size)
                }
            }
    }
}