package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable, Parcelable {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("display_singular")
    private String display_singular;
    @SerializedName("display_plural")
    private String display_plural;
    @SerializedName("create_at")
    private long created_at;
    @SerializedName("update_at")
    private long updated_at;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplay_singular() {
        return display_singular;
    }

    public String getDisplay_plural() {
        return display_plural;
    }

    public float getCreated_at() {
        return created_at;
    }

    public float getUpdated_at() {
        return updated_at;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplay_singular(String display_singular) {
        this.display_singular = display_singular;
    }

    public void setDisplay_plural(String display_plural) {
        this.display_plural = display_plural;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.display_singular);
        dest.writeString(this.display_plural);
        dest.writeLong(this.created_at);
        dest.writeLong(this.updated_at);
    }

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.display_singular = in.readString();
        this.display_plural = in.readString();
        this.created_at = in.readLong();
        this.updated_at = in.readLong();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
