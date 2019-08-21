package com.statemachinegenerator.smg.libmethods;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.guard.Guard;

@LibMethod
@Qualifier("guard")
public class LibGuard {

    public Guard<String, String> gurad1(){
        return (ctx) -> {
            System.out.println("---- guard1 (not lib) ----");
            return true;
        };
    }

    public Guard<String, String> gurad2(){
        return (ctx) -> {
            System.out.println("---- guard2 (not lib) ----");
            return false;
        };
    }

}
