package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Unit implements Serializable, Parcelable {

    public static final String UNIT_METRIC = "metric";
    public static final String UNIT_IMPERIAL = "imperial";

    @SerializedName("name")
    private String name;
    @SerializedName("abbreviation")
    private String abbreviation;
    @SerializedName("display_singular")
    private String display_singular;
    @SerializedName("display_plural")
    private String display_plural;
    @SerializedName("system")
    private String system;

    public Unit() {
    }

    // Getter Methods

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDisplay_singular() {
        return display_singular;
    }

    public String getDisplay_plural() {
        return display_plural;
    }

    public String getSystem() {
        return system;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setDisplay_singular(String display_singular) {
        this.display_singular = display_singular;
    }

    public void setDisplay_plural(String display_plural) {
        this.display_plural = display_plural;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", display_singular='" + display_singular + '\'' +
                ", display_plural='" + display_plural + '\'' +
                ", system='" + system + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.abbreviation);
        dest.writeString(this.display_singular);
        dest.writeString(this.display_plural);
        dest.writeString(this.system);
    }

    protected Unit(Parcel in) {
        this.name = in.readString();
        this.abbreviation = in.readString();
        this.display_singular = in.readString();
        this.display_plural = in.readString();
        this.system = in.readString();
    }

    public static final Parcelable.Creator<Unit> CREATOR = new Parcelable.Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel source) {
            return new Unit(source);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };
}
