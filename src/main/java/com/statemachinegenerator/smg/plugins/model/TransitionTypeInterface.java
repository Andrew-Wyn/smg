package com.statemachinegenerator.smg.plugins.model;

import com.bmeme.lib.libmethods.LibAction;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface TransitionTypeInterface {

    boolean check(Class typeTransition);

    StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception;

    default List<Method> getMethod(Object libMethods){
        List<Method> methods = new ArrayList<>();

        Collections.addAll(methods, libMethods.getClass().getMethods());

        return methods;
    }
}
