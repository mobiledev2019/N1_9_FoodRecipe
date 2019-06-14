package com.dangth.foodrecipe.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dangth.foodrecipe.fragment.CarouselTabFragment;
import com.dangth.foodrecipe.fragment.HomeTabFragment;
import com.dangth.foodrecipe.services.model.FeedResponse;
import com.dangth.foodrecipe.services.model.HomeTabModel;

import java.util.List;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private HomeTabModel homeTabModel;
    private List<FeedResponse.FeedItem> carouselList;
    public HomePagerAdapter(FragmentManager fm, HomeTabModel homeTabModel, List<FeedResponse.FeedItem> carouselList) {
        super(fm);
        this.homeTabModel = homeTabModel;
        this.carouselList = carouselList;
    }

    @Override
    public Fragment getItem(int i) {
        if (i==0) {
            return HomeTabFragment.getInstance(homeTabModel);
        }
        return CarouselTabFragment.getInstance(i - 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Home";
        return carouselList.get(position - 1).getName();
    }

    @Override
    public int getCount() {
        return carouselList.size() + 1;
    }

}
