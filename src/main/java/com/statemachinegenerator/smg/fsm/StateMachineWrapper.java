package com.statemachinegenerator.smg.fsm;

import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.service.FSMConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StateMachineWrapper {

    // ---- DEPENDENCIES ----
    private final StateMachineBuild stateMachineBuild;
    private final FSMConfigurationService service;

    // singleton
    private StateMachine<String, String> stateMachine;

    public void builAndStartFSM(String id) throws Exception{

        if(Objects.isNull(stateMachine) || stateMachine.isComplete()){
            stateMachine = stateMachineBuild.buildStateMachine(service.getElement(id));
            stateMachine.start();
        } else {
            System.out.println("can't build, another state machine is running ...");
        }

    }

    public void fire(String event) throws Exception{
        stateMachine.sendEvent(event);
    }
}
