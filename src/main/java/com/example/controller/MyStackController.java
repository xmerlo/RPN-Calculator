package com.example.controller;

import com.example.model.MyStack;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rpn")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MyStackController {
    private List<MyStack> myStacks = new ArrayList<MyStack>();
    private int nbCreatedStacks = 0;

    private int getStackIndexById(int id){
        for(int i = 0; i < myStacks.size(); i++){
            if(myStacks.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    @GetMapping("/stack")
    @Operation(summary = "List the available stacks")
    public List<MyStack> getMyStacks(){
        return myStacks;
    }

    @PostMapping("/stack")
    @Operation(summary = "Create a new stack")
    public MyStack newStack(){
        MyStack newStack = new MyStack(nbCreatedStacks);
        nbCreatedStacks++;
        myStacks.add(newStack);
        return newStack;
    }

    @PostMapping(path = "/stack/{id}")
    @Operation(summary = "Push a new value to a stack")
    public MyStack newValue(@PathVariable("id") int id, @RequestParam int value){
        int stackIndex = getStackIndexById(id);
        if (stackIndex != -1){
            MyStack tmpStack = myStacks.get(stackIndex);
            List<Integer> tmpValues = tmpStack.getValues();
            tmpValues.add(value);
            tmpStack.setValues(tmpValues);
            myStacks.set(stackIndex, tmpStack);
            return tmpStack;
        }
        return null;
    }

    @PostMapping(path = "/stack/{id}/clear")
    @Operation(summary = "Clear a stack")
    public MyStack cleanStack(@PathVariable("id") int id){
        int stackIndex = getStackIndexById(id);
        if (stackIndex != -1){
            MyStack tmpStack = myStacks.get(stackIndex);
            tmpStack.getValues().clear();
            myStacks.set(stackIndex, tmpStack);
            return tmpStack;
        }
        return null;
    }

    @GetMapping(path = "/stack/{id}")
    @Operation(summary = "Get a stack")
    public MyStack getValues(@PathVariable("id") int id){
        int stackIndex = getStackIndexById(id);
        if (stackIndex != -1)
            return myStacks.get(stackIndex);
        return null;
    }

    @DeleteMapping(path = { "/stack/{id}" })
    @Operation(summary = "Delete a stack")
    public MyStack deleteStack(@PathVariable("id") int id){
        int stackIndex = getStackIndexById(id);
            if (stackIndex != -1){
                MyStack tmpStack = myStacks.get(stackIndex);
                myStacks.remove(stackIndex);
                return tmpStack;
            }
        return null;
    }

    @GetMapping(path = "/op")
    @Operation(summary = "List of all operators")
    public String getOperators(){
        return "'+' : Addition\n" +
                "'-' : Substraction\n" +
                "'*' : Multiplication\n" +
                "'/' : Division";
    }

    @PostMapping(path = "/op/{op}/stack/{stack_id}")
    @Operation(summary = "Apply an operand to a stack")
    public MyStack applyOperandToAStack(@PathVariable("op") char op, @PathVariable("stack_id") int stack_id){
        int stackIndex = getStackIndexById(stack_id);
        if (stackIndex != -1){
            MyStack tmpStack = myStacks.get(stackIndex);
            if(tmpStack.getValues().size() > 1)
            {
                int a = tmpStack.getValues().get(tmpStack.getValues().size()-1);
                int b = tmpStack.getValues().get(tmpStack.getValues().size()-2);
                int result = 0;

                switch (op){
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        result = a / b;
                        break;
                }

                tmpStack.getValues().set(tmpStack.getValues().size()-2, result);
                tmpStack.getValues().remove(tmpStack.getValues().size()-1);
            }
            return tmpStack;
        }
        return null;
    }
}
