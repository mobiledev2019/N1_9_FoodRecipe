package com.dangth.foodrecipe.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchRecipeResponse implements Serializable {
    @SerializedName("count")
    private Integer count;
    @SerializedName("next")
    private String next;
    @SerializedName("prev")
    private String prev;
    @SerializedName("results")
    private List<Recipe> results;

    public SearchRecipeResponse(Integer count, String next, String prev, List<Recipe> results) {
        this.count = count;
        this.next = next;
        this.prev = prev;
        this.results = results;
    }

    public SearchRecipeResponse() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SearchRecipeResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", prev='" + prev + '\'' +
                ", results=" + results +
                '}';
    }
}
