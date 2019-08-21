package com.statemachinegenerator.smg.plugins.plugin;

import com.bmeme.lib.libmethods.LibAction;
import com.bmeme.lib.libmethods.LibGuard;
import com.statemachinegenerator.smg.domain.structures.ChoiceState;
import com.statemachinegenerator.smg.domain.transitions.ChoiceTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
//import com.statemachinegenerator.smg.libmethods.LibAction;
//import com.statemachinegenerator.smg.libmethods.LibGuard;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.ChoiceTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// improve

@TransitionPlugin
public class ChoiceTransitionPlugin implements TransitionTypeInterface {

    private final Class typeTransition = ChoiceTransition.class;

    @Override
    public boolean check(Class typeTransition) {
        return this.typeTransition.getName().equals(typeTransition.getName());
    }

    @Override
    public StateMachineTransitionConfigurer<String, String> processTransition(Transition transition, StateMachineTransitionConfigurer<String, String> transitionConfigurer, ApplicationContext applicationContext) throws Exception{
        ChoiceTransition choiceTransition = (ChoiceTransition) transition;


        Object actions = applicationContext.getBean(LibAction.class);
        Object guards = applicationContext.getBean(LibGuard.class);

        List<Method> actionMethods = getMethod(actions);
        List<Method> guardMethods = getMethod(guards);

        Method action;
        Method guard;

        ChoiceTransitionConfigurer<String, String> choiceTransitionConfigurer = transitionConfigurer
                .withChoice()
                .source(choiceTransition.getSource());

        action = actionMethods.stream().filter(m -> m.getName().equals(choiceTransition.getFirst().getAction())).findFirst().orElse(null);
        guard = guardMethods.stream().filter(m -> m.getName().equals(choiceTransition.getFirst().getGuard())).findFirst().orElse(null);

        choiceTransitionConfigurer = choiceTransitionConfigurer.first(
                choiceTransition.getFirst().getValue(),
                Objects.nonNull(action) ? (Guard<String, String>)action.invoke(actions) : (ctx) -> true,
                Objects.nonNull(guard) ? (Action<String, String>)guard.invoke(actions) : (ctx) -> {}
        );

        for(ChoiceState then : choiceTransition.getThens()){
            action = actionMethods.stream().filter(m -> m.getName().equals(then.getAction())).findFirst().orElse(null);
            guard = guardMethods.stream().filter(m -> m.getName().equals(then.getGuard())).findFirst().orElse(null);

            choiceTransitionConfigurer = choiceTransitionConfigurer.then(
                    then.getValue(),
                    Objects.nonNull(action) ? (Guard<String, String>)action.invoke(actions) : (ctx) -> true,
                    Objects.nonNull(guard) ? (Action<String, String>)guard.invoke(actions) : (ctx) -> {}
            );
        }


        return  choiceTransitionConfigurer
                .last(choiceTransition.getLast())
                .and();
    }
}
