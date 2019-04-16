package com.dangth.foodrecipe.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dangth.foodrecipe.R;

import java.util.Objects;


public class OptionDialogFragment extends DialogFragment {
    public static final String SHARED_PREFERENCES_NAME = "FILTER";
    public static final String SHARED_PREFERENCES_VEGETARIAN = "VEGETARIAN";
    private RadioGroup rgVegetarian;
    private RadioButton rbVegetarianTrue;
    private RadioButton rbVegetarianFalse;

    private Context context;
    private OnDialogDismissListener onDialogDismissListener;
    private boolean vegetarian = false;

    public static OptionDialogFragment newInstance() {
        OptionDialogFragment optionDialogFragment = new OptionDialogFragment();
        return optionDialogFragment;
    }

    public void setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        this.onDialogDismissListener = onDialogDismissListener;
    }

    private void getSharedData() {
        Context context = Objects.requireNonNull(getContext());

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean vegetarian = sharedPreferences.getBoolean(SHARED_PREFERENCES_VEGETARIAN, false);
        rbVegetarianTrue.setChecked(vegetarian);
        rbVegetarianFalse.setChecked(!vegetarian);
    }

    private void writeSharedData(boolean vegetarian) {
        Context context = Objects.requireNonNull(getContext());
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SHARED_PREFERENCES_VEGETARIAN, vegetarian);
        editor.apply();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgVegetarian = view.findViewById(R.id.rbVegetarianGroup);
        rbVegetarianTrue = view.findViewById(R.id.rbVegetarianTrue);
        rbVegetarianFalse = view.findViewById(R.id.rbVegetarianFalse);
        Button btnSave = view.findViewById(R.id.btnSave);
        getSharedData();

        rgVegetarian.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbVegetarianTrue :
                    vegetarian = true;
                    break;
                case R.id.rbVegetarianFalse :
                    vegetarian = false;
                    break;
                default :
                    vegetarian = false;
                    break;
            }
        });
        btnSave.setOnClickListener(v -> {
            writeSharedData(vegetarian);
            this.dismiss();
            onDialogDismissListener.dismiss();
        });
    }

    public interface OnDialogDismissListener {
        void dismiss();
    }
}
