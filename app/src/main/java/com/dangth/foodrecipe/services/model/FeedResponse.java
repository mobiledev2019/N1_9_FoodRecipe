package com.dangth.foodrecipe.services.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import  java.io.Serializable;

public final class FeedResponse implements Serializable {
    @SerializedName("next")
    private final String next;
    @SerializedName("results")
    private final List<FeedItem> results;

    public static final class FeedItem implements Serializable {
        @SerializedName("item")
        private final Recipe content;
        @SerializedName("name")
        private final String name;
        @SerializedName("type")
        private final String type;

        public FeedItem(String str, String str2, Recipe obj) {
            this.type = str;
            this.name = str2;
            this.content = obj;
        }

        public final String getType() {
            return this.type;
        }

        public final String getName() {
            return this.name;
        }

        public final Recipe getContent() {
            return this.content;
        }

        @Override
        public String toString() {
            return "FeedItem{" +
                    "content=" + content +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public FeedResponse(List<FeedItem> list, String str) {
        this.results = list;
        this.next = str;
    }

    public final List<FeedItem> getResults() {
        return this.results;
    }

    public final String getNext() {
        return this.next;
    }

    @Override
    public String toString() {
        return "FeedResponse{" +
                "next='" + next + '\'' +
                ", results=" + results +
                '}';
    }
}
