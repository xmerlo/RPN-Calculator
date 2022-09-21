package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class MyStack {

    private final int id;

    List<Integer> values;

    public MyStack(){
        this.id = 0;
        this.values = new ArrayList<>();
    }

    public MyStack(int id){
        this.id = id;
        this.values = new ArrayList<>();
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public int getId() {
        return id;
    }
}
