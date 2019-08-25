package com.statemachinegenerator.smg.plugins.plugin;


import com.bmeme.lib.libannotation.annotations.LibAction;
import com.bmeme.lib.libannotation.annotations.LibGuard;
import com.statemachinegenerator.smg.domain.structures.MethodInvoke;
import com.statemachinegenerator.smg.domain.transitions.ExternalTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.*;

import static com.statemachinegenerator.smg.fsm.StateMachineBuild.getMethod;

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

        Collection<Object> actions = applicationContext.getBeansWithAnnotation(LibAction.class).values();
        Collection<Object> guards = applicationContext.getBeansWithAnnotation(LibGuard.class).values();

        MethodInvoke action = getMethod(actions, externalTransition.getAction());
        MethodInvoke errorAction = getMethod(actions, externalTransition.getErrorAction());
        MethodInvoke guard = getMethod(guards, externalTransition.getGuard());

        // add guard

        return transitionConfigurer
                .withExternal()
                .source(externalTransition.getSource())
                .target(externalTransition.getTarget())
                .event(externalTransition.getEvent())
                .action(
                        Objects.nonNull(action) ? (Action<String, String>)action.getMethod().invoke(action.getObject()) : (ctx) -> {},
                        Objects.nonNull(errorAction) ? (Action<String, String>)errorAction.getMethod().invoke(errorAction.getObject()) : (ctx) -> {}
                        )
                .guard(
                        Objects.nonNull(guard) ? (Guard<String, String>)guard.getMethod().invoke(guard.getObject()) : null//(ctx) -> true
                )
                .and();
    }
}
