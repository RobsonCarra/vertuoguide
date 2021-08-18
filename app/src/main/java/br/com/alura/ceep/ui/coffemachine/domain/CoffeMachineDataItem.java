package br.com.alura.ceep.ui.coffemachine.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@org.parceler.Parcel

public class CoffeMachineDataItem implements Serializable {
    private String CoffeType;

    public CoffeMachineDataItem (String coffeType, String coffeIntensity,
                                 Integer coffeImage) {
        CoffeType = coffeType;
        CoffeImage = coffeImage;
    }

    protected CoffeMachineDataItem (Parcel in) {
        CoffeType = in.readString ();
        if (in.readByte () == 0) {
            CoffeImage = null;
        } else {
            CoffeImage = in.readInt ();
        }
    }

    public int describeContents () {
        return 0;
    }

    public static final Parcelable.Creator<CoffeMachineDataItem> CREATOR = new Parcelable.Creator<CoffeMachineDataItem> () {
        @Override
        public CoffeMachineDataItem createFromParcel (Parcel in) {
            return new CoffeMachineDataItem (in);
        }

        @Override
        public CoffeMachineDataItem[] newArray (int size) {
            return new CoffeMachineDataItem[size];
        }
    };

    public String getCoffeType () {
        return CoffeType;
    }

    public void setCoffeType (String coffeType) {
        CoffeType = coffeType;
    }

    public Integer getCoffeImage () {
        return CoffeImage;
    }

    public void setCoffeImage (Integer coffeImage) {
        CoffeImage = coffeImage;
    }

    private Integer CoffeImage;
}
