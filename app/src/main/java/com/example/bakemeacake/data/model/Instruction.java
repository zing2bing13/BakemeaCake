package com.example.bakemeacake.data.model;

public class Instruction {
    public int ID;
    public int Recipe_ID;
    public int Step;
    public String Instruction;

    public Instruction() {
        this.ID = 0;
        this.Recipe_ID = 0;
        this.Step = 0;
        this.Instruction = "";
    }

    public Instruction(int id, int recipe_id, int step, String instruction){
        this.ID = id;
        this.Recipe_ID = recipe_id;
        this.Step = step;
        this.Instruction = instruction;
    }
}
