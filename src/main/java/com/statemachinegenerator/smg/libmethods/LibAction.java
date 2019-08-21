package com.statemachinegenerator.smg.libmethods;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.action.Action;

@LibMethod
@Qualifier("guard")
public class LibAction {

    public Action<String, String> action1(){
        return (ctx) -> {
            System.out.println("---- action1 (not lib) ----");
        };
    }

    public Action<String, String> action2(){
        return (ctx) -> {
            System.out.println("---- action2 (not lib) ----");
        };
    }

}
