package com.statemachinegenerator.smg.libmethods;

import org.springframework.statemachine.guard.Guard;

@LibMethod
public class LibGuard {

    public Guard<String, String> gurad1(){
        return (ctx) -> {
            System.out.println("---- guard1 ----");
            return true;
        };
    }

    public Guard<String, String> gurad2(){
        return (ctx) -> {
            System.out.println("---- guard2 ----");
            return false;
        };
    }

}
