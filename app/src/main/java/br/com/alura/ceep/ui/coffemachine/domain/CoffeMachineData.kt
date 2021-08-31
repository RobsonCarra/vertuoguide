package br.com.alura.ceep.ui.coffemachine.domain

import android.widget.ImageView
import java.io.Serializable

class CoffeMachineData : Serializable {
    var Coffetype: String?
    var CoffeIntensity: String?
    var CoffeDescription: String?
    var CoffeSize: String?
    var CoffeImage: Int

    constructor(
        coffeType: String?, coffeIntensity: String?,
        coffeDescription: String?, coffeSize: String?, coffeImage: Int
    ) {
        Coffetype = coffeType
        CoffeIntensity = coffeIntensity
        CoffeDescription = coffeDescription
        CoffeSize = coffeSize
        CoffeImage = coffeImage
    }

//    protected constructor(`in`: Parcel) {
//        Coffetype = `in`.readString()
//        CoffeIntensity = `in`.readString()
//        CoffeIntensityTitule = `in`.readString()
//        CoffeDescription = `in`.readString()
//        CoffeSize = `in`.readString()
//        CoffeSizeTitule = `in`.readString()
//        if (`in`.readByte().toString()) {
//            CoffeImage = null
//        } else {
//            CoffeImage = `in`.readInt()
//        }
//    }

//    fun describeContents(): Int {
//        return 0
//    }


    //    companion object {
//        val CREATOR: Parcelable.Creator<CoffeMachineData> =
//            object : Parcelable.Creator<CoffeMachineData?> {
//                override fun createFromParcel(`in`: Parcel): CoffeMachineData? {
//                    return CoffeMachineData(`in`)
//                }
//
//                override fun newArray(size: Int): Array<CoffeMachineData?> {
//                    return arrayOfNulls(size)
//                }
//            }
//    }

//    open fun getCoffeType(): String? {
//        return coffeType
//    }
//
//    fun setCoffeType(coffeType: String) {
//        CoffeType = coffeType
//    }
//
//    fun getCoffeIntensity(): String? {
//        return coffeIntensity
//    }
//
//    fun setCoffeIntensity(coffeIntensity: String) {
//        CoffeIntensity = coffeIntensity
//    }
//
//    fun getCoffeIntensityTitule(): String? {
//        return CoffeIntensityTitule
//    }
//
//    fun setCoffeIntensityTitule(coffeIntensityTitule: String) {
//        CoffeIntensityTitule = coffeIntensityTitule
//    }
//
//    fun getCoffeDescription(): String? {
//        return CoffeDescription
//    }
//
//    fun setCoffeDescription(coffeDescription: String) {
//        CoffeDescription = coffeDescription
//    }
//
//    fun getCoffeSizeTitule(): String? {
//        return CoffeSizeTitule
//    }
//
//    fun setCoffeSizeTitule(coffeSizeTitule: String) {
//        CoffeSizeTitule = coffeSizeTitule
//    }
//
//    fun getCoffeSize(): String? {
//        return CoffeSize
//    }
//
//    fun setCoffeSize(coffeSize: String) {
//        CoffeSize = coffeSize
//    }
//
//    fun getCoffeImage(): Int? {
//        return CoffeImage
//    }
//
//    fun setCoffeImage(coffeImage: Int?) {
//        CoffeImage = coffeImage
//    }
//
//    private var CoffeImage: Int? = null

}
