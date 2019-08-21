package com.statemachinegenerator.smg.libmethods;


import org.springframework.statemachine.action.Action;

@LibMethod
public class LibAction {

    public Action<String, String> action1(){
        return (ctx) -> {
            System.out.println("---- action1 ----");
        };
    }

    public Action<String, String> action2(){
        return (ctx) -> {
            System.out.println("---- action2 ----");
        };
    }

}
