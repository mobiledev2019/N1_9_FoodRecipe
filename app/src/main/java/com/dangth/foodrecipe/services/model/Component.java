package com.dangth.foodrecipe.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Component implements Serializable {
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
}
