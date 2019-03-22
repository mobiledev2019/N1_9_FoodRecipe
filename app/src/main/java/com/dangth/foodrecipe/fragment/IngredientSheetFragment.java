package com.dangth.foodrecipe.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.IngredientAdapter;
import com.dangth.foodrecipe.services.model.Section;
import com.dangth.foodrecipe.services.model.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IngredientSheetFragment extends BottomSheetDialogFragment {

    private List<Section> sections;

    public IngredientSheetFragment() {
        super();
    }

    public static IngredientSheetFragment newInstance(ArrayList<Section> sections) {
        IngredientSheetFragment ingredientSheetFragment = new IngredientSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("in", sections);
        ingredientSheetFragment.setArguments(bundle);
        return ingredientSheetFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            sections = (List<Section>) getArguments().getSerializable("in");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_sheet_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        IngredientAdapter adapter = new IngredientAdapter(sections, Unit.UNIT_METRIC);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider_item);
        itemDecor.setDrawable(Objects.requireNonNull(drawable));
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}
