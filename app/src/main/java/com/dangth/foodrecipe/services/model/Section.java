package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Section implements Serializable, Parcelable {
    @SerializedName("components")
    private List<Component> components;
    @SerializedName("name")
    private String name;
    @SerializedName("position")
    private int position;

    public Section() {
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.components);
        dest.writeString(this.name);
        dest.writeInt(this.position);
    }

    protected Section(Parcel in) {
        this.components = new ArrayList<Component>();
        in.readList(this.components, Component.class.getClassLoader());
        this.name = in.readString();
        this.position = in.readInt();
    }

    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
