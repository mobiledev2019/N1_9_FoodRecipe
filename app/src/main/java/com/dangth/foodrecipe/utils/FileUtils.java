package com.dangth.foodrecipe.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.dangth.foodrecipe.services.model.FeedResponse;
import com.dangth.foodrecipe.services.model.Recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    private static final String OFFLINE_DATA = "offline.dat";
    private static final String LIKE_DATA = "like.dat";

    public static boolean writeOfflineData(FeedResponse feedResponse, Context context) {

        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(OFFLINE_DATA, Context.MODE_PRIVATE));
            oos.writeObject(feedResponse);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static FeedResponse readOfflineData(Context context) {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(context.openFileInput(OFFLINE_DATA));
            FeedResponse feedResponse = (FeedResponse) ois.readObject();
            ois.close();
            Toast.makeText(context, "You are in offline mode", Toast.LENGTH_SHORT).show();
            return feedResponse;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isOfflineDataExist(File filesDir) {
        File f = new File(filesDir + File.separator + OFFLINE_DATA);
        return f.exists();
    }

    public static boolean writeLikeData(File filesDir, HashMap<Integer, Recipe> likeData) {
        ObjectOutputStream oos;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(filesDir + File.separator + LIKE_DATA));

            oos = new ObjectOutputStream(fos);
            oos.writeObject(likeData);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HashMap<Integer, Recipe> readLikeData(File filesDir) {
        if (!isLikeDataExist(filesDir)) {
            Log.i("LIKE_DATA", "readLikeData: LIKE_DATA is not exist");
            return null;
        }
        ObjectInputStream ois;
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(filesDir + File.separator + LIKE_DATA));

            ois = new ObjectInputStream(fis);
            HashMap<Integer, Recipe> likeData = (HashMap<Integer, Recipe>) ois.readObject();
            ois.close();
            Log.i("LIKE_DATA", "readLikeData: " + likeData);
            return likeData;
        } catch (IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLikeDataExist(File filesDir) {
        File f = new File(filesDir + File.separator + LIKE_DATA);
        return f.exists();
    }

}
