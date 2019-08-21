package com.statemachinegenerator.smg.plugins.plugin;

import com.bmeme.lib.libmethods.LibAction;
import com.statemachinegenerator.smg.domain.transitions.InternalTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
//import com.statemachinegenerator.smg.libmethods.LibAction;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.InternalTransitionConfigurer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@TransitionPlugin
public class InternalTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = InternalTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception{
        InternalTransition internalTransition = (InternalTransition) transition;

        Object actions = applicationContext.getBean(LibAction.class);

        List<Method> actionMethods = getMethod(actions);

        Method action = actionMethods.stream().filter(m -> m.getName().equals(internalTransition.getAction())).findFirst().orElse(null);
        Method errorAction = actionMethods.stream().filter(m -> m.getName().equals(internalTransition.getErrorAction())).findFirst().orElse(null);

        InternalTransitionConfigurer<String, String> internalTransitionConfigurer = transitionConfigurer.withInternal();

        internalTransitionConfigurer = internalTransitionConfigurer.source(internalTransition.getSource());

        internalTransitionConfigurer = internalTransitionConfigurer.source(internalTransition.getSource());

        if(Objects.nonNull(internalTransition.getTimer()))
            internalTransitionConfigurer = internalTransitionConfigurer.timer(internalTransition.getTimer());
        if(Objects.nonNull(internalTransition.getTimerOnce()))
            internalTransitionConfigurer = internalTransitionConfigurer.timerOnce(internalTransition.getTimerOnce());

        internalTransitionConfigurer = internalTransitionConfigurer.action(
                Objects.nonNull(action) ? (Action<String, String>)action.invoke(actions) : (ctx) -> {},
                Objects.nonNull(errorAction) ? (Action<String, String>)errorAction.invoke(actions) : (ctx) -> {}
        );

        return internalTransitionConfigurer.and();
    }
}
