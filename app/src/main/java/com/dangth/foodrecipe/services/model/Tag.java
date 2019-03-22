package com.dangth.foodrecipe.services.model;

import java.io.Serializable;

public class Tag implements Serializable {
    private String display_name;
    private Integer id;
    private String name;
    private String type;

    public Tag(String display_name, Integer id, String name, String type) {
        this.display_name = display_name;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "display_name='" + display_name + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
