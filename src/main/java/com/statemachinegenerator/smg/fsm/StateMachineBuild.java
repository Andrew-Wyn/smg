package com.statemachinegenerator.smg.fsm;

import com.bmeme.lib.libannotation.annotations.LibAction;
import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.domain.structures.MethodInvoke;
import com.statemachinegenerator.smg.domain.structures.Region;
import com.statemachinegenerator.smg.domain.structures.State;
import com.statemachinegenerator.smg.domain.transitions.Transition;
import com.statemachinegenerator.smg.plugins.model.TransitionPlugin;
import com.statemachinegenerator.smg.plugins.model.TransitionTypeInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
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

        StateMachine<String, String> stateMachine = builder.build();

        stateMachine.getStateMachineAccessor()
                .doWithRegion(function -> function.addStateMachineInterceptor(
                        new StateMachineInterceptorAdapter<String, String>() {
                            @Override
                            public Exception stateMachineError(StateMachine<String, String> stateMachine1,
                                                               Exception exception) {

                                log.error(String.format("error occured '{}'", exception.getMessage()));

                                // return null indicating handled error
                                return null;
                            }
                        }));

        return stateMachine;
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

        Collection<Object> actions = applicationContext.getBeansWithAnnotation(LibAction.class).values();

        MethodInvoke entryAction;
        MethodInvoke exitAction;

        for(State state : states){

            entryAction = getMethod(actions, state.getEntryAction());
            exitAction = getMethod(actions, state.getExitAction());

            stateConfigurer = stateConfigurer.state(
                    state.getValue(),
                    Objects.nonNull(entryAction) ? (Action<String, String>)entryAction.getMethod().invoke(entryAction.getObject()) : (ctx) -> {},
                    Objects.nonNull(exitAction) ? (Action<String, String>)exitAction.getMethod().invoke(exitAction.getObject()) : (ctx) -> {}
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

    public static MethodInvoke getMethod(Collection<Object> libsMethods, String find){

        for(Object libMethods : libsMethods){
            List<Method> methods = new ArrayList<>();
            Collections.addAll(methods, libMethods.getClass().getMethods());

            Method out = methods.stream().filter(m -> m.getName().equals(find)).findFirst().orElse(null);

            if(Objects.nonNull(out))
                return new MethodInvoke(out, libMethods);

        }

        return null;
    }

}
