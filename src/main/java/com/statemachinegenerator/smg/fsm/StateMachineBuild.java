package com.statemachinegenerator.smg.fsm;

import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.domain.structures.State;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StateMachineBuild {

    private final ApplicationContext applicationContext;

    public StateMachine<String, String> buildStateMachine(FSMConfiguration fsmConfiguration) throws Exception{
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        // configuration
        makeConfiguration(fsmConfiguration, builder);

        // states
        makeStates(fsmConfiguration, builder);

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

    private void makeStates(FSMConfiguration fsmConfiguration, StateMachineBuilder.Builder<String, String> builder) throws Exception{
        StateConfigurer<String, String> stateConfigurer = builder.configureStates().withStates();

        List<State> states = fsmConfiguration.getStates();

        stateConfigurer = stateConfigurer.initial(states.get(0).getValue());

        for(State state : states){

            stateConfigurer = stateConfigurer.state(state.getValue());

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

        stateConfigurer.end(states.get(states.size()-1).getValue());
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
