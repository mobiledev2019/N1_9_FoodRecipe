package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Compilation implements Serializable, Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail_url")
    private String thumbnail_url;
    @SerializedName("video_url")
    private String video_url;
    @SerializedName("recipes")
    private List<Recipe> recipes;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumbnail_url);
        dest.writeString(this.video_url);
        dest.writeTypedList(this.recipes);
    }

    public Compilation() {
    }

    protected Compilation(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.thumbnail_url = in.readString();
        this.video_url = in.readString();
        this.recipes = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Creator<Compilation> CREATOR = new Creator<Compilation>() {
        @Override
        public Compilation createFromParcel(Parcel source) {
            return new Compilation(source);
        }

        @Override
        public Compilation[] newArray(int size) {
            return new Compilation[size];
        }
    };
}
