package com.example.bakemeacake.ui.recipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bakemeacake.R;
import com.example.bakemeacake.data.InstructionsAdapter;
import com.example.bakemeacake.data.model.Instruction;
import com.example.bakemeacake.data.model.Recipe;
import com.example.bakemeacake.util.DatabaseHandler;

import java.util.ArrayList;

public class InstructionsActivity extends Fragment {

    private Recipe recipe = new Recipe();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter instructionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHandler dbHandler = null;
    private ArrayList<Instruction> instructions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.dbHandler = new DatabaseHandler(this.getContext());

        this.instructions = (ArrayList) dbHandler.GetInstructions(recipe.ID);

        View instructionView = inflater.inflate(R.layout.content_instructions, container, false);
        recyclerView = instructionView.findViewById(R.id.recycler_instructions);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        instructionAdapter = new InstructionsAdapter(instructions);
        recyclerView.setAdapter(instructionAdapter);
        Button addButton = instructionView.findViewById(R.id.button_add_instructions);

        addButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                dbHandler = new DatabaseHandler(v.getContext());
                EditText instructionText = getView().findViewById(R.id.editInstructions);
                String instructionName = instructionText.getText().toString();
                Instruction instruction = new Instruction();
                instruction.Instruction = instructionName;
                instruction.Recipe_ID = recipe.ID;
                dbHandler.CreateInstruction(instruction);
                dbHandler.close();
                instructionAdapter.notifyDataSetChanged();
            }
        });

        return instructionView;
    }
    public void setRecipeID(int recipeId){
        recipe.ID = recipeId;
    }

}
