package br.com.alura.ceep.ui.coffemachine.domain;

import java.io.Serializable;

public class CoffeMachineDataItem implements Serializable {
    private String CoffeType;
    private String Capsules;

    public CoffeMachineDataItem (String coffeType, String capsules, Integer coffeImage) {
        CoffeType = coffeType;
        Capsules = capsules;
        CoffeImage = coffeImage;
    }

    public void setCapsules (String capsules) {
        Capsules = capsules;
    }

    public String getCapsules () {
        return Capsules;
    }

    public int describeContents () {
        return 0;
    }

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

    public CoffeMachineDataItem[] newArray (int size) {
        return new CoffeMachineDataItem[size];
    }}