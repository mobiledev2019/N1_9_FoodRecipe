package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Instruction implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("display_text")
    private String displayText;
    @SerializedName("position")
    private Integer position;
    @SerializedName("start_time")
    private long startTime;
    @SerializedName("end_time")
    private long endTime;

    public Instruction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", displayText='" + displayText + '\'' +
                ", position=" + position +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

}