package com.dangth.foodrecipe.services.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.gson.annotations.SerializedName;

public class Suggestion implements SearchSuggestion {
    @SerializedName("display")
    private String display;

    public Suggestion(String display) {
        this.display = display;
    }
    public Suggestion(Parcel source) {
        this.display = source.readString();
    }

    public Suggestion() {
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "display='" + display + '\'' +
                '}';
    }

    public static final Creator<Suggestion> CREATOR = new Creator<Suggestion>() {
        @Override
        public Suggestion createFromParcel(Parcel source) {
            return new Suggestion(source);
        }

        @Override
        public Suggestion[] newArray(int size) {
            return new Suggestion[size];
        }
    };

    @Override
    public String getBody() {
        return display;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(display);
    }
}
