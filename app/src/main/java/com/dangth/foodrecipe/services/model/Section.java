package com.dangth.foodrecipe.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable {
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

}
