package br.com.alura.ceep.ui.coffemachine.domain

import android.widget.ImageView
import java.io.Serializable

class CoffeMachineDataItem  (
    var CoffeType: String?,
    var CoffeImage: Int
)
//    constructor(
//        coffeType: String?, coffeIntensity: String?,
//        coffeImage: Int
//    ) {
//        CoffeType = coffeType
//        CoffeImage = coffeImage
//    }

//    protected constructor(`in`: Parcel) {
//        coffeType = `in`.readString()
//        if (`in`.readByte().toInt() == 0) {
//            coffeImage = null
//        } else {
//            coffeImage = `in`.readInt()
//        }
//    }
//
//    fun describeContents(): Int {
//        return 0
//    }

    //    companion object {
//        val CREATOR: Parcelable.Creator<CoffeMachineDataItem> =
//            object : Parcelable.Creator<CoffeMachineDataItem?> {
//                override fun createFromParcel(`in`: Parcel): CoffeMachineDataItem? {
//                    return CoffeMachineDataItem(`in`)
//                }
//
//                override fun newArray(size: Int): Array<CoffeMachineDataItem?> {
//                    return arrayOfNulls(size)
//                }
//            }
//    }
