package com.dangth.foodrecipe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.activity.CompilationActivity;
import com.dangth.foodrecipe.activity.HomeActivity;
import com.dangth.foodrecipe.activity.MainActivity;
import com.dangth.foodrecipe.activity.RecipeActivity;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.services.model.Compilation;
import com.dangth.foodrecipe.services.model.HomeTabModel;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeTabFragment extends Fragment {
    private static final String HOME_TAB_KEY = "home_tab";

    private ImageView featureImage;
    private TextView featureText;
    private RecyclerView recyclerView;

    public HomeTabFragment() {
    }

    public static HomeTabFragment getInstance(HomeTabModel homeTabModel) {
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(HOME_TAB_KEY, homeTabModel);
        homeTabFragment.setArguments(bundle);
        return homeTabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        featureImage = view.findViewById(R.id.ivFeatureItem);
        featureText = view.findViewById(R.id.featureTitle);
        recyclerView = view.findViewById(R.id.rvRecent);
        Bundle bundle = getArguments();
        if (bundle != null && getContext() != null && getActivity() != null) {
            HomeTabModel model = bundle.getParcelable(HOME_TAB_KEY);
            GlideApp.with(getContext())
                    .load(model.featureItem.getContent().getThumbnail_url())
                    .transition(withCrossFade())
                    .centerCrop()
                    .into(featureImage);
            featureText.setText(model.featureItem.getContent().getName());

            featureImage.setOnClickListener(v -> {
                if (!model.featureItem.getContent().isComplition()) {
                    Intent intent = new Intent(getContext(), RecipeActivity.class);
                    intent.putExtra(RecipeListAdapter.INTENT_RECIPE_ACTIVITY, (Parcelable) model.featureItem.getContent());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), CompilationActivity.class);
                    intent.putExtra(RecipeListAdapter.INTENT_COMPILATION_ACTIVITY, (Parcelable) model.featureItem.getContent());
                    startActivity(intent);
                }
            });

            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            RecipeListAdapter adapter = new RecipeListAdapter(getContext(), R.layout.recycler_view_item);
            adapter.setRecipeList(((HomeActivity) getActivity()).recentRecipe);
            recyclerView.setAdapter(adapter);
        }


    }
}
