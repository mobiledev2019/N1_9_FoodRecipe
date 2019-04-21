package com.dangth.foodrecipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.activity.CompilationActivity;
import com.dangth.foodrecipe.activity.MainActivity;
import com.dangth.foodrecipe.activity.RecipeActivity;
import com.dangth.foodrecipe.services.model.Compilation;
import com.dangth.foodrecipe.services.model.Recipe;
import com.dangth.foodrecipe.utils.DpiUtils;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    public static final String INTENT_RECIPE_ACTIVITY = "INTENT_RECIPE_ACTIVITY";
    public static final String INTENT_COMPILATION_ACTIVITY = "INTENT_COMPILATION_ACTIVITY";
    private List<Recipe> recipeList;
    private Context context;
    private int layout;

    public RecipeListAdapter(List<Recipe> recipeList, Context context, int layout) {
        this.recipeList = recipeList;
        this.context = context;
        this.layout = layout;
    }

    public RecipeListAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(layout, viewGroup, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        Recipe recipe = recipeList.get(i);
        TextView tvRecipeTitle = recipeViewHolder.tvRecipeTitle;
        ImageView ivRecipe = recipeViewHolder.ivRecipe;
        tvRecipeTitle.setText(recipe.getName());
        GlideApp.with(context)
                .load(recipe.getThumbnail_url())
                .transition(withCrossFade())
                .centerCrop()
                .into(ivRecipe);
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvRecipeTitle;
        ImageView ivRecipe;
        ConstraintLayout constraintLayout;
        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            ivRecipe = itemView.findViewById(R.id.ivItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe sendRecipe = recipeList.get(getAdapterPosition());
            if (!sendRecipe.isComplition()) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(INTENT_RECIPE_ACTIVITY, (Parcelable) sendRecipe);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, CompilationActivity.class);
                Compilation compilation = new Compilation();
                compilation.setRecipes(sendRecipe.getRecipes());
                compilation.setThumbnail_url(sendRecipe.getThumbnail_url());
                compilation.setName(sendRecipe.getName());
                compilation.setVideo_url(sendRecipe.getVideo_url());
                intent.putExtra(INTENT_COMPILATION_ACTIVITY, (Parcelable) compilation);
                context.startActivity(intent);
            }
        }
    }
}
