package com.dangth.foodrecipe.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.activity.HomeActivity;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.services.model.FeedResponse;
import com.dangth.foodrecipe.services.model.Recipe;

import java.util.List;

public class CarouselTabFragment extends Fragment {
    private static final String CTF_KEY = "ctf_key";

    public static CarouselTabFragment getInstance(int position) {
        CarouselTabFragment ctf = new CarouselTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CTF_KEY, position);
        ctf.setArguments(bundle);
        return ctf;
    }

    public CarouselTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.carousel_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvCarousel);
        view.findViewById(R.id.title).setVisibility(View.GONE);
        Bundle bundle = getArguments();
        if (bundle != null && getContext() != null && getActivity() != null) {
            int pos = bundle.getInt(CTF_KEY);
            FeedResponse.FeedItem carousel = ((HomeActivity) getActivity()).carouselList.get(pos);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            RecipeListAdapter adapter = new RecipeListAdapter(getContext(), R.layout.recycler_view_item);
            adapter.setRecipeList(carousel.getCarouselItem());
            recyclerView.setAdapter(adapter);
        }
    }
}
