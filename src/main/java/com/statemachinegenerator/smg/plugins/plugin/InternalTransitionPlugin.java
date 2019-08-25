package com.statemachinegenerator.smg.plugins.plugin;

import com.bmeme.lib.libannotation.annotations.LibAction;
import com.statemachinegenerator.smg.domain.structures.MethodInvoke;
import com.statemachinegenerator.smg.domain.transitions.InternalTransition;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.InternalTransitionConfigurer;

import java.util.Collection;
import java.util.Objects;

import static com.statemachinegenerator.smg.fsm.StateMachineBuild.getMethod;

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

        Collection<Object> actions = applicationContext.getBeansWithAnnotation(LibAction.class).values();

        MethodInvoke action = getMethod(actions, internalTransition.getAction());
        MethodInvoke errorAction = getMethod(actions, internalTransition.getErrorAction());

        InternalTransitionConfigurer<String, String> internalTransitionConfigurer = transitionConfigurer.withInternal();

        internalTransitionConfigurer = internalTransitionConfigurer.source(internalTransition.getSource());

        internalTransitionConfigurer = internalTransitionConfigurer.source(internalTransition.getSource());

        if(Objects.nonNull(internalTransition.getTimer()))
            internalTransitionConfigurer = internalTransitionConfigurer.timer(internalTransition.getTimer());
        if(Objects.nonNull(internalTransition.getTimerOnce()))
            internalTransitionConfigurer = internalTransitionConfigurer.timerOnce(internalTransition.getTimerOnce());

        internalTransitionConfigurer = internalTransitionConfigurer.action(
                Objects.nonNull(action) ? (Action<String, String>)action.getMethod().invoke(action.getObject()) : (ctx) -> {},
                Objects.nonNull(errorAction) ? (Action<String, String>)errorAction.getMethod().invoke(action.getObject()) : (ctx) -> {}
        );

        return internalTransitionConfigurer.and();
    }
}
