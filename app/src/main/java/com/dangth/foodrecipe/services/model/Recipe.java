package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe implements Serializable, Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("instructions")
    private List<Instruction> instructions;
    @SerializedName("name")
    private String name;
    @SerializedName("sections")
    private List<Section> sections;
    @SerializedName("thumbnail_url")
    private String thumbnail_url;
    @SerializedName("video_url")
    private String video_url;
    @SerializedName("recipes")
    private List<Recipe> recipes;

    public boolean like = false;
    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public String getName() {
        return name;
    }

    public List<Section> getSections() {
        return sections;
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

    public boolean isComplition() {
        return recipes != null;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeList(this.instructions);
        dest.writeString(this.name);
        dest.writeTypedList(this.sections);
        dest.writeString(this.thumbnail_url);
        dest.writeString(this.video_url);
        dest.writeTypedList(this.recipes);
        dest.writeByte(this.like ? (byte) 1 : (byte) 0);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.instructions = new ArrayList<Instruction>();
        in.readList(this.instructions, Instruction.class.getClassLoader());
        this.name = in.readString();
        this.sections = in.createTypedArrayList(Section.CREATOR);
        this.thumbnail_url = in.readString();
        this.video_url = in.readString();
        this.recipes = in.createTypedArrayList(Recipe.CREATOR);
        this.like = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
