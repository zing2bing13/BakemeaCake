package com.example.bakemeacake.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.model.Instruction;

import java.util.ArrayList;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionViewHolder> {
        private ArrayList<Instruction> instructions;

        public static class InstructionViewHolder extends RecyclerView.ViewHolder {
            private TextView insTextView;

            public InstructionViewHolder(View itemView) {
                super(itemView);
                this.insTextView = itemView.findViewById(R.id.ItemTextView);
            }
        }

        public InstructionsAdapter(ArrayList<Instruction> instructions) {
            this.instructions = instructions;
        }

        public InstructionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate((R.layout.frame_item_textview), parent, false);
            InstructionViewHolder vh = new InstructionViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(InstructionViewHolder holder, int position) {
            holder.insTextView.setText(instructions.get(position).Instruction);
        }

        public int getItemCount(){
            return instructions.size();
        }
    }
