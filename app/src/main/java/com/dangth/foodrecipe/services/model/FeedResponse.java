package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import  java.io.Serializable;

public final class FeedResponse implements Serializable, Parcelable {
    @SerializedName("next")
    private final String next;
    @SerializedName("results")
    private final List<FeedItem> results;

    public static final class FeedItem implements Serializable, Parcelable {
        @SerializedName("item")
        private final Recipe content;
        @SerializedName("items")
        private final List<Recipe> carouselItem;
        @SerializedName("name")
        private final String name;
        @SerializedName("type")
        private final String type;

        public FeedItem(String str, String str2, Recipe obj, List<Recipe> carouselItem) {
            this.type = str;
            this.name = str2;
            this.content = obj;
            this.carouselItem = carouselItem;
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

        public List<Recipe> getCarouselItem() {
            return carouselItem;
        }

        @Override
        public String toString() {
            return "FeedItem{" +
                    "content=" + content +
                    ", carouselItem=" + carouselItem +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.content, flags);
            dest.writeTypedList(this.carouselItem);
            dest.writeString(this.name);
            dest.writeString(this.type);
        }

        protected FeedItem(Parcel in) {
            this.content = in.readParcelable(Recipe.class.getClassLoader());
            this.carouselItem = in.createTypedArrayList(Recipe.CREATOR);
            this.name = in.readString();
            this.type = in.readString();
        }

        public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>() {
            @Override
            public FeedItem createFromParcel(Parcel source) {
                return new FeedItem(source);
            }

            @Override
            public FeedItem[] newArray(int size) {
                return new FeedItem[size];
            }
        };
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.next);
        dest.writeTypedList(this.results);
    }

    protected FeedResponse(Parcel in) {
        this.next = in.readString();
        this.results = in.createTypedArrayList(FeedItem.CREATOR);
    }

    public static final Parcelable.Creator<FeedResponse> CREATOR = new Parcelable.Creator<FeedResponse>() {
        @Override
        public FeedResponse createFromParcel(Parcel source) {
            return new FeedResponse(source);
        }

        @Override
        public FeedResponse[] newArray(int size) {
            return new FeedResponse[size];
        }
    };
}
