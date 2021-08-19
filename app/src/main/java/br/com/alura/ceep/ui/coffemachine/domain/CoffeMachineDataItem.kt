package br.com.alura.ceep.ui.coffemachine.domain

import android.os.Parcelable
import org.parceler.Parcel
import java.io.Serializable

@Parcel
class CoffeMachineDataItem : Serializable {
    var coffeType: String?

    constructor(
        coffeType: String?, coffeIntensity: String?,
        coffeImage: Int?
    ) {
        this.coffeType = coffeType
        this.coffeImage = coffeImage
    }

    protected constructor(`in`: android.os.Parcel) {
        coffeType = `in`.readString()
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
        val CREATOR: Parcelable.Creator<CoffeMachineDataItem> =
            object : Parcelable.Creator<CoffeMachineDataItem?> {
                override fun createFromParcel(`in`: android.os.Parcel): CoffeMachineDataItem? {
                    return CoffeMachineDataItem(`in`)
                }

                override fun newArray(size: Int): Array<CoffeMachineDataItem?> {
                    return arrayOfNulls(size)
                }
            }
    }
}