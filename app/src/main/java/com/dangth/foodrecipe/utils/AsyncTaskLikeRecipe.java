package com.dangth.foodrecipe.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.dangth.foodrecipe.services.model.Recipe;

import java.io.File;
import java.util.HashMap;

public class AsyncTaskLikeRecipe extends AsyncTask<Void, Void, Void> {
    private Recipe recipe;
    private File fileDir;

    public AsyncTaskLikeRecipe(Recipe recipe, File fileDir) {
        this.recipe = recipe;
        this.fileDir = fileDir;
    }

    @SuppressLint("UseSparseArrays")
    private static void likeRecipe(Recipe recipe, File fileDir) {
        HashMap<Integer, Recipe> likeData = FileUtils.readLikeData(fileDir);
        if (likeData == null) {
            likeData = new HashMap<>();
            likeData.put(recipe.getId(), recipe);
            FileUtils.writeLikeData(fileDir, likeData);
            Log.i("LIKE_DATA", "likeRecipe: Done");
        }
        else {
            if (recipe.like) {
                likeData.put(recipe.getId(), recipe);
                Log.i("LIKE_DATA", "likeRecipe: Done");
            } else {
                likeData.remove(recipe.getId());
                Log.i("LIKE_DATA", "likeRecipe: Remove " + recipe.getId());
            }
            FileUtils.writeLikeData(fileDir, likeData);
        }
        Log.i("LIKE_DATA", "likeRecipe: " + likeData);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        likeRecipe(recipe, fileDir);
        return null;
    }
}
