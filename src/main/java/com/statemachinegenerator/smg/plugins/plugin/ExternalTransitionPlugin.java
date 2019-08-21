package com.statemachinegenerator.smg.plugins.plugin;

import com.bmeme.lib.libmethods.LibAction;
import com.statemachinegenerator.smg.domain.transitions.ExternalTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
//import com.statemachinegenerator.smg.libmethods.LibAction;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.lang.reflect.Method;
import java.util.*;

@TransitionPlugin
public class ExternalTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = ExternalTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception{
        ExternalTransition externalTransition = (ExternalTransition) transition;

        Object actions = applicationContext.getBean(LibAction.class);

        List<Method> actionMethods = getMethod(actions);

        Method action = actionMethods.stream().filter(m -> m.getName().equals(externalTransition.getAction())).findFirst().orElse(null);
        Method errorAction = actionMethods.stream().filter(m -> m.getName().equals(externalTransition.getErrorAction())).findFirst().orElse(null);

        return transitionConfigurer
                .withExternal()
                .source(externalTransition.getSource())
                .target(externalTransition.getTarget())
                .event(externalTransition.getEvent())
                .action(
                        Objects.nonNull(action) ? (Action<String, String>)action.invoke(actions) : (ctx) -> {},
                        Objects.nonNull(errorAction) ? (Action<String, String>)errorAction.invoke(actions) : (ctx) -> {}
                        )
                .and();
    }
}
