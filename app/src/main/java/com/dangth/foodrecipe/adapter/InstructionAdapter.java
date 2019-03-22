package com.dangth.foodrecipe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.services.model.Instruction;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionHolder> {

    private List<Instruction> instructionList;
    private Context context;

    public InstructionAdapter(Context context) {
        this.context = context;
    }

    public void setInstructionList(List<Instruction> instructionList) {
        this.instructionList = instructionList;
    }

    @NonNull
    @Override
    public InstructionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.preparation_item, viewGroup, false);
        return new InstructionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionHolder instructionHolder, int i) {
        Instruction instruction = instructionList.get(i);
        TextView tvInstructionNumber = instructionHolder.tvInstructionNumber;
        TextView tvInstructionText = instructionHolder.tvInstructionText;

        tvInstructionNumber.setText(String.valueOf(instruction.getPosition()));
        tvInstructionText.setText(instruction.getDisplayText());
    }

    @Override
    public int getItemCount() {
        return instructionList == null ? 0 : instructionList.size();
    }

    class InstructionHolder extends RecyclerView.ViewHolder {
        private TextView tvInstructionNumber;
        private TextView tvInstructionText;
        InstructionHolder(@NonNull View itemView) {
            super(itemView);
            tvInstructionNumber = itemView.findViewById(R.id.instruction_number);
            tvInstructionText = itemView.findViewById(R.id.instruction_text);
        }
    }
}
