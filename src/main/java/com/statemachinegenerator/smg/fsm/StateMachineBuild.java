package com.statemachinegenerator.smg.fsm;

import com.bmeme.lib.libmethods.LibAction;
import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.domain.structures.Region;
import com.statemachinegenerator.smg.domain.structures.State;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Component
@RequiredArgsConstructor
public class StateMachineBuild {

    private final ApplicationContext applicationContext;

    public StateMachine<String, String> buildStateMachine(FSMConfiguration fsmConfiguration) throws Exception{
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        // configuration
        makeConfiguration(fsmConfiguration, builder);

        // states
        makeStates(fsmConfiguration.getInitial(), fsmConfiguration.getEnd(), fsmConfiguration.getStates(), builder.configureStates().withStates());

        //regions
        makeRegions(fsmConfiguration, builder);

        // transictions
        makeTransictions(fsmConfiguration, builder);

        return builder.build();
    }

    private void makeConfiguration(FSMConfiguration fsmConfiguration, StateMachineBuilder.Builder<String, String> builder) throws Exception{
        builder.configureConfiguration().withConfiguration()
                .autoStartup(fsmConfiguration.getAutoStartUp())
                .machineId(fsmConfiguration.getMachineId())
                .listener(new StateMachineEventListener());
    }

    private void makeStates(String initial, String end, List<State> states, StateConfigurer<String, String> stateConfigurer) throws Exception{
        if(Objects.nonNull(initial))
            stateConfigurer = stateConfigurer.initial(initial);

        Object actions = applicationContext.getBeansOfType(LibAction.class);

        List<Method> actionMethods = new ArrayList<>();

        Collections.addAll(actionMethods, actions.getClass().getMethods());

        Method entryAction;
        Method exitAction;

        for(State state : states){

            entryAction = actionMethods.stream().filter(m -> m.getName().equals(state.getEntryAction())).findFirst().orElse(null);
            exitAction = actionMethods.stream().filter(m -> m.getName().equals(state.getExitAction())).findFirst().orElse(null);

            stateConfigurer = stateConfigurer.state(
                    state.getValue(),
                    Objects.nonNull(entryAction) ? (Action<String, String>)entryAction.invoke(actions) : (ctx) -> {},
                    Objects.nonNull(exitAction) ? (Action<String, String>)exitAction.invoke(actions) : (ctx) -> {}
                    );

            switch(state.getType()){
                case CHOICE:
                    stateConfigurer = stateConfigurer.choice(state.getValue()); break;
                case JUNCTION:
                    stateConfigurer = stateConfigurer.junction(state.getValue()); break;
                case FORK:
                    stateConfigurer = stateConfigurer.fork(state.getValue()); break;
                case JOIN:
                    stateConfigurer = stateConfigurer.join(state.getValue()); break;

            }
        }

        if(Objects.nonNull(end))
            stateConfigurer.initial(end);
    }

    private void makeRegions(FSMConfiguration fsmConfiguration, StateMachineBuilder.Builder<String, String> builder) throws Exception{

        StateConfigurer<String, String> stateConfigurer = builder.configureStates().withStates();

        for(Region region : fsmConfiguration.getRegions()){

            stateConfigurer = stateConfigurer.parent(region.getParent());

            makeStates(region.getInitial(), region.getEnd(), region.getStates(), stateConfigurer);

        }
    }

    private void makeTransictions(FSMConfiguration fsmConfiguration, StateMachineBuilder.Builder<String, String> builder) throws Exception{
        StateMachineTransitionConfigurer<String, String> transitionConfigurer = builder.configureTransitions();

        Collection<Object> plugins = applicationContext.getBeansWithAnnotation(TransitionPlugin.class).values();

        for(Transition transition : fsmConfiguration.getTransitions()){
            for(Object plugin : plugins){
                TransitionTypeInterface castedPlugin = (TransitionTypeInterface)plugin;
                System.out.println(transition.getClass());
                if(castedPlugin.check(transition.getClass())){
                    transitionConfigurer = castedPlugin.processTransition(transition, transitionConfigurer, applicationContext);
                    break;
                }
            }
        }
    }

}
