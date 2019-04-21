package com.dangth.foodrecipe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.services.model.FeedResponse;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private Context context;
    private List<FeedResponse.FeedItem> carouselList;

    public CarouselAdapter(Context context) {
        this.context = context;
    }

    public void setCarouselList(List<FeedResponse.FeedItem> carouselList) {
        this.carouselList = carouselList;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.carousel_item, viewGroup, false);
        return new CarouselViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder carouselViewHolder, int i) {
        FeedResponse.FeedItem feedItem = carouselList.get(i);

        RecyclerView recyclerView = carouselViewHolder.recyclerView;
        TextView tvTitle = carouselViewHolder.tvTitle;

        tvTitle.setText(feedItem.getName());
        Log.i("DEMO", "loadDataFeedView: " + feedItem.getName());
        RecipeListAdapter adapter = new RecipeListAdapter(feedItem.getCarouselItem(), context, R.layout.carousel_recyclerview_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return carouselList != null ? carouselList.size() : 0;
    }

    class CarouselViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvTitle;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rvCarousel);
            tvTitle = itemView.findViewById(R.id.title);
        }
    }
}
