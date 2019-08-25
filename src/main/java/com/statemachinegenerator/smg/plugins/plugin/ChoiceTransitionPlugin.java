package com.statemachinegenerator.smg.plugins.plugin;

import com.bmeme.lib.libannotation.annotations.LibAction;
import com.bmeme.lib.libannotation.annotations.LibGuard;
import com.statemachinegenerator.smg.domain.structures.ChoiceTransitionState;
import com.statemachinegenerator.smg.domain.structures.MethodInvoke;
import com.statemachinegenerator.smg.domain.transitions.ChoiceTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.ChoiceTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.*;

import static com.statemachinegenerator.smg.fsm.StateMachineBuild.getMethod;

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

        Collection<Object> actions = applicationContext.getBeansWithAnnotation(LibAction.class).values();
        Collection<Object> guards = applicationContext.getBeansWithAnnotation(LibGuard.class).values();

        MethodInvoke action;
        MethodInvoke guard;

        ChoiceTransitionConfigurer<String, String> choiceTransitionConfigurer = transitionConfigurer
                .withChoice()
                .source(choiceTransition.getSource());

        action = getMethod(actions, choiceTransition.getFirst().getAction());
        guard = getMethod(guards, choiceTransition.getFirst().getGuard());

        choiceTransitionConfigurer = choiceTransitionConfigurer.first(
                choiceTransition.getFirst().getValue(),
                Objects.nonNull(action) ? (Guard<String, String>)action.getMethod().invoke(action.getObject()) : (ctx) -> true,
                Objects.nonNull(guard) ? (Action<String, String>)guard.getMethod().invoke(guard.getObject()) : (ctx) -> {}
        );

        for(ChoiceTransitionState then : choiceTransition.getThens()){
            action = getMethod(actions, then.getAction());
            guard = getMethod(guards, then.getGuard());

            choiceTransitionConfigurer = choiceTransitionConfigurer.then(
                    then.getValue(),
                    Objects.nonNull(action) ? (Guard<String, String>)action.getMethod().invoke(action.getObject()) : (ctx) -> true,
                    Objects.nonNull(guard) ? (Action<String, String>)guard.getMethod().invoke(guard.getObject()) : (ctx) -> {}
            );
        }


        return  choiceTransitionConfigurer
                .last(choiceTransition.getLast())
                .and();
    }
}
