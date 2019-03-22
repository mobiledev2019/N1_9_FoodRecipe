package com.dangth.foodrecipe.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {
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
}
