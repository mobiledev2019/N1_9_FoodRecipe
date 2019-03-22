package com.dangth.foodrecipe.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.services.model.Component;
import com.dangth.foodrecipe.services.model.Ingredient;
import com.dangth.foodrecipe.services.model.Measurement;
import com.dangth.foodrecipe.services.model.Section;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Section> sectionList;
    private List<Component> ingredientsComponent;
    private String system;

    public IngredientAdapter(List<Section> sectionList, String system) {
        this.sectionList = sectionList;
        this.system = system;

        if (sectionList != null) {
            ingredientsComponent = new ArrayList<>();
            extractIngredient();
        }
    }

    private void extractIngredient() {
        for (Section section : sectionList) {
            ingredientsComponent.addAll(section.getComponents());
        }
    }
    private Measurement getMeasurementByMetric(List<Measurement> measurements) {
        if (measurements.size() == 1) return measurements.get(0);

        for (Measurement measurement : measurements) {
            if (measurement.getUnit().getSystem().equals(system)) return measurement;
        }
        return null;
    }

    private String buildMeasurementString(Measurement measurement) {
        if (measurement == null) return "";
        float quantity;
        try {
             quantity = Float.parseFloat(measurement.getQuantity());
        } catch (NumberFormatException ignored) {
            quantity = -1;
        }
        StringBuilder measureUnit = new StringBuilder();
        if (quantity == 1 || quantity == -1) {
            measureUnit
                    .append(measurement.getQuantity())
                    .append(" ")
                    .append(measurement.getUnit().getDisplay_singular());
            return measureUnit.toString();
        }
        else if (quantity == 0) {
            return "";
        }
        else {
            measureUnit
                    .append(measurement.getQuantity())
                    .append(" ")
                    .append(measurement.getUnit().getDisplay_plural());
            return measureUnit.toString();
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.ingredient_item, viewGroup, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int i) {
        TextView ingredientName = ingredientViewHolder.ingredientName;
        TextView ingredientSize = ingredientViewHolder.ingredientSize;

        Component component = ingredientsComponent.get(i);
        Ingredient ingredient = component.getIngredient();
        Measurement measurement = getMeasurementByMetric(ingredientsComponent.get(i).getMeasurementList());

        String measure = buildMeasurementString(measurement);
        ingredientName.setText(measure.isEmpty() ? component.getRawText() : ingredient.getName());
        ingredientSize.setText(measure);
    }

    @Override
    public int getItemCount() {
        return ingredientsComponent == null ? 0 : ingredientsComponent.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        TextView ingredientSize;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientSize = itemView.findViewById(R.id.ingredient_size);
        }
    }
}
