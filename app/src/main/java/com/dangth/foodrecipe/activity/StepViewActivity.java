package com.dangth.foodrecipe.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.StepViewAdapter;
import com.dangth.foodrecipe.services.model.Instruction;

import java.util.List;


public class StepViewActivity extends AppCompatActivity {

    public List<Instruction> instructionList;
    public String url;
    public String thumbnail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);

        instructionList = getIntent().getParcelableArrayListExtra("instruction");
        url = getIntent().getStringExtra("url");
        thumbnail = getIntent().getStringExtra("thumbnail");
        StepViewAdapter adapter = new StepViewAdapter(getSupportFragmentManager(), instructionList);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
