package br.com.alura.ceep.ui.coffemachine.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@org.parceler.Parcel

public class CoffeMachineData implements Serializable {
    private String CoffeType;
    private String CoffeIntensity;
    private String CoffeIntensityTitule;
    private String CoffeDescription;
    private String CoffeSize;
    private String CoffeSizeTitule;

    public CoffeMachineData (String coffeType, String coffeIntensity, String coffeIntensityTitule,
                             String coffeDescription, String coffeSizeTitule, String coffeSize, Integer coffeImage) {
        CoffeType = coffeType;
        CoffeIntensity = coffeIntensity;
        CoffeIntensityTitule = coffeIntensityTitule;
        CoffeDescription = coffeDescription;
        CoffeSizeTitule = coffeSizeTitule;
        CoffeSize = coffeSize;
        CoffeImage = coffeImage;
    }

    protected CoffeMachineData (Parcel in) {
        CoffeType = in.readString ();
        CoffeIntensity = in.readString ();
        CoffeIntensityTitule = in.readString ();
        CoffeDescription = in.readString ();
        CoffeSize = in.readString ();
        CoffeSizeTitule = in.readString ();
        if (in.readByte () == 0) {
            CoffeImage = null;
        } else {
            CoffeImage = in.readInt ();
        }
    }

    public int describeContents () {
        return 0;
    }

    public static final Parcelable.Creator<CoffeMachineData> CREATOR = new Parcelable.Creator<CoffeMachineData> () {
        @Override
        public CoffeMachineData createFromParcel (Parcel in) {
            return new CoffeMachineData (in);
        }

        @Override
        public CoffeMachineData[] newArray (int size) {
            return new CoffeMachineData[size];
        }
    };

    public String getCoffeType () {
        return CoffeType;
    }

    public void setCoffeType (String coffeType) {
        CoffeType = coffeType;
    }

    public String getCoffeIntensity () {
        return CoffeIntensity;
    }

    public void setCoffeIntensity (String coffeIntensity) {
        CoffeIntensity = coffeIntensity;
    }

    public String getCoffeIntensityTitule () {
        return CoffeIntensityTitule;
    }

    public void setCoffeIntensityTitule (String coffeIntensityTitule) {
        CoffeIntensityTitule = coffeIntensityTitule;
    }

    public String getCoffeDescription () {
        return CoffeDescription;
    }

    public void setCoffeDescription (String coffeDescription) {
        CoffeDescription = coffeDescription;
    }

    public String getCoffeSizeTitule () {
        return CoffeSizeTitule;
    }

    public void setCoffeSizeTitule (String coffeSizeTitule) {
        CoffeSizeTitule = coffeSizeTitule;
    }

    public String getCoffeSize () {
        return CoffeSize;
    }

    public void setCoffeSize (String coffeSize) {
        CoffeSize = coffeSize;
    }

    public Integer getCoffeImage () {
        return CoffeImage;
    }

    public void setCoffeImage (Integer coffeImage) {
        CoffeImage = coffeImage;
    }

    private Integer CoffeImage;
}
