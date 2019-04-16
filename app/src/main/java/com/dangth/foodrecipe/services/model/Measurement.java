package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Measurement implements Serializable, Parcelable {
    @SerializedName("id")
    private long id;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("unit")
    private Unit unit;

    public Measurement() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    @NonNull
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", quantity='" + quantity + '\'' +
                ", unit=" + unit +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.quantity);
        dest.writeSerializable(this.unit);
    }

    protected Measurement(Parcel in) {
        this.id = in.readLong();
        this.quantity = in.readString();
        this.unit = (Unit) in.readSerializable();
    }

    public static final Parcelable.Creator<Measurement> CREATOR = new Parcelable.Creator<Measurement>() {
        @Override
        public Measurement createFromParcel(Parcel source) {
            return new Measurement(source);
        }

        @Override
        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };
}
