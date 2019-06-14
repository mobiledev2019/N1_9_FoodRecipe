package com.dangth.foodrecipe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.services.model.Recipe;
import com.dangth.foodrecipe.utils.FileUtils;

import java.util.ArrayList;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Map<Integer, Recipe> likeData = FileUtils.readLikeData(getFilesDir());

        TextView tvTitle = findViewById(R.id.title);
        RecyclerView recyclerView = findViewById(R.id.rvCarousel);

        tvTitle.setText("LIKE");
        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.recycler_view_item);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (likeData != null) {
            adapter.setRecipeList(new ArrayList<>(likeData.values()));
        }
        else adapter.setRecipeList(null);

        recyclerView.setAdapter(adapter);
    }
}
