package com.dangth.foodrecipe.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SuggestResponse implements Serializable {
    @SerializedName("results")
    private List<Suggestion> results;

    public SuggestResponse(List<Suggestion> results) {
        this.results = results;
    }

    public SuggestResponse() {
    }

    public List<Suggestion> getResults() {
        return results;
    }

    public void setResults(List<Suggestion> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SuggestResponse{" +
                "results=" + results +
                '}';
    }
}
