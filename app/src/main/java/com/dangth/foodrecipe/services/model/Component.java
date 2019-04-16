package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Component implements Serializable, Parcelable {
    @SerializedName("id")
    private long id;
    @SerializedName("raw_text")
    private String rawText;
    @SerializedName("extra_comment")
    private String extraComment;
    @SerializedName("position")
    private int position;
    @SerializedName("ingredient")
    private Ingredient ingredient;
    @SerializedName("measurements")
    private List<Measurement> measurementList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public String getExtraComment() {
        return extraComment;
    }

    public void setExtraComment(String extraComment) {
        this.extraComment = extraComment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }

    @Override
    public String toString() {
        return "Component{" +
                "rawText='" + rawText + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.rawText);
        dest.writeString(this.extraComment);
        dest.writeInt(this.position);
        dest.writeSerializable(this.ingredient);
        dest.writeList(this.measurementList);
    }

    public Component() {
    }

    protected Component(Parcel in) {
        this.id = in.readLong();
        this.rawText = in.readString();
        this.extraComment = in.readString();
        this.position = in.readInt();
        this.ingredient = (Ingredient) in.readSerializable();
        this.measurementList = new ArrayList<Measurement>();
        in.readList(this.measurementList, Measurement.class.getClassLoader());
    }

    public static final Parcelable.Creator<Component> CREATOR = new Parcelable.Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel source) {
            return new Component(source);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };
}
