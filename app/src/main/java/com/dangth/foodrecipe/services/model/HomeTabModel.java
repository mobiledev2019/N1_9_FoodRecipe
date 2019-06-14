package com.dangth.foodrecipe.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeTabModel implements Parcelable {
    public FeedResponse.FeedItem featureItem;
    public List<Recipe> recipeList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.featureItem, flags);
        dest.writeTypedList(this.recipeList);
    }

    public HomeTabModel() {
    }

    protected HomeTabModel(Parcel in) {
        this.featureItem = in.readParcelable(FeedResponse.FeedItem.class.getClassLoader());
        this.recipeList = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Parcelable.Creator<HomeTabModel> CREATOR = new Parcelable.Creator<HomeTabModel>() {
        @Override
        public HomeTabModel createFromParcel(Parcel source) {
            return new HomeTabModel(source);
        }

        @Override
        public HomeTabModel[] newArray(int size) {
            return new HomeTabModel[size];
        }
    };
}
