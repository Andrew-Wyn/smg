package com.statemachinegenerator.smg.fsm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
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
        log.info("state entered: " + state.getId());
    }

    @Override
    public void stateExited(State<String, String> state) {
        log.info("state exited " + state.getId());
    }

    @Override
    public void transition(Transition<String, String> transition) {
        log.info("transition : " + transition.toString() + " - id: " + transition.hashCode());
    }

    @Override
    public void transitionStarted(Transition<String, String> transition) {
        log.info("transition started");
    }

    @Override
    public void transitionEnded(Transition<String, String> transition) {
        log.info("transition ended");
    }

    @Override
    public void stateMachineStarted(StateMachine<String, String> stateMachine) {
        log.info("statemachine started : " + stateMachine.getId());
    }

    @Override
    public void stateMachineStopped(StateMachine<String, String> stateMachine) {
        log.info("statemachine stopped : " + stateMachine.getId());
    }

    @Override
    public void eventNotAccepted(Message<String> event) {
        log.info("event not accepted - headers : " + event.getHeaders() + " - payload  : " + event.getPayload());
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.info("exended state changed - key : " + key + " - value : " + value);
    }

    @Override
    public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
        log.error("an error has occurred -> " + exception.getMessage());
    }

}