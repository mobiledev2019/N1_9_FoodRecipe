package com.dangth.foodrecipe.services.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Measurement implements Serializable {
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
}
