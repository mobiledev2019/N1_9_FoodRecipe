package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable, Parcelable {

    @SerializedName("compilations")
    private List<Compilation> compilations;
    @SerializedName("id")
    private int id;
    @SerializedName("instructions")
    private List<Instruction> instructions;
    @SerializedName("name")
    private String name;
    @SerializedName("num_servings")
    private int num_servings;
    @SerializedName("sections")
    private List<Section> sections;
    @SerializedName("servings_noun_plural")
    private String servings_noun_plural;
    @SerializedName("servings_noun_singular")
    private String servings_noun_singular;
    @SerializedName("slug")
    private String slug;
    @SerializedName("thumbnail_url")
    private String thumbnail_url;
    @SerializedName("video_url")
    private String video_url;
    @SerializedName("recipes")
    private List<Recipe> recipes;

    public Recipe() {
    }

    public List<Compilation> getCompilations() {
        return compilations;
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

    public int getNum_servings() {
        return num_servings;
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getServings_noun_plural() {
        return servings_noun_plural;
    }

    public String getServings_noun_singular() {
        return servings_noun_singular;
    }

    public String getSlug() {
        return slug;
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
        return compilations == null && recipes != null;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.compilations);
        dest.writeInt(this.id);
        dest.writeList(this.instructions);
        dest.writeString(this.name);
        dest.writeInt(this.num_servings);
        dest.writeList(this.sections);
        dest.writeString(this.servings_noun_plural);
        dest.writeString(this.servings_noun_singular);
        dest.writeString(this.slug);
        dest.writeString(this.thumbnail_url);
        dest.writeString(this.video_url);
        dest.writeTypedList(this.recipes);
    }

    protected Recipe(Parcel in) {
        this.compilations = new ArrayList<Compilation>();
        in.readList(this.compilations, Compilation.class.getClassLoader());
        this.id = in.readInt();
        this.instructions = new ArrayList<Instruction>();
        in.readList(this.instructions, Instruction.class.getClassLoader());
        this.name = in.readString();
        this.num_servings = in.readInt();
        this.sections = new ArrayList<Section>();
        in.readList(this.sections, Section.class.getClassLoader());
        this.servings_noun_plural = in.readString();
        this.servings_noun_singular = in.readString();
        this.slug = in.readString();
        this.thumbnail_url = in.readString();
        this.video_url = in.readString();
        this.recipes = in.createTypedArrayList(Recipe.CREATOR);
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
