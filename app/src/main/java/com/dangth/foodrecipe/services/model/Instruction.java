package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Instruction implements Serializable, Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.displayText);
        dest.writeValue(this.position);
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
    }

    protected Instruction(Parcel in) {
        this.id = in.readLong();
        this.displayText = in.readString();
        this.position = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startTime = in.readLong();
        this.endTime = in.readLong();
    }

    public static final Parcelable.Creator<Instruction> CREATOR = new Parcelable.Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel source) {
            return new Instruction(source);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };
}