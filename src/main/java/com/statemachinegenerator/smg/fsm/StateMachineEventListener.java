package com.statemachinegenerator.smg.fsm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Objects;

@Slf4j
public class StateMachineEventListener
        extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateChanged(State<String, String> from, State<String, String> to) {
        if(Objects.nonNull(from))
            log.info("state changed: " + from.getId() + " -> " + to.getId());
    }

    @Override
    public void stateEntered(State<String, String> state) {
    }

    @Override
    public void stateExited(State<String, String> state) {
    }

    @Override
    public void transition(Transition<String, String> transition) {
    }

    @Override
    public void transitionStarted(Transition<String, String> transition) {
    }

    @Override
    public void transitionEnded(Transition<String, String> transition) {
    }

    @Override
    public void stateMachineStarted(StateMachine<String, String> stateMachine) {
    }

    @Override
    public void stateMachineStopped(StateMachine<String, String> stateMachine) {
    }

    @Override
    public void eventNotAccepted(Message<String> event) {
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
    }

    @Override
    public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
    }

    @Override
    public void stateContext(StateContext<String, String> stateContext) {
    }
}