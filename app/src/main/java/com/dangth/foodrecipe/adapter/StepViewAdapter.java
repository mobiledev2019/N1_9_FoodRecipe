package com.dangth.foodrecipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dangth.foodrecipe.fragment.StepViewFragment;
import com.dangth.foodrecipe.services.model.Instruction;

import java.util.List;

public class StepViewAdapter extends FragmentStatePagerAdapter {

    private List<Instruction> instructionList;

    public StepViewAdapter(FragmentManager fm, List<Instruction> instructions) {
        super(fm);
        this.instructionList = instructions;
    }

    @Override
    public Fragment getItem(int i) {
        return StepViewFragment.getInstance(i);
    }

    @Override
    public int getCount() {
        return instructionList.size();
    }
}
