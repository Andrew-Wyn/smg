package com.statemachinegenerator.smg.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FSMConfiguration {

    @Id
    private String id;

    private List<State> states;
    private List<Transition> transitions;

    private String machineId;
    private Boolean autoStartUp;

    public FSMConfiguration(List<State> states, List<Transition> transitions, String machineId, Boolean autoStartUp) {
        this.states = states;
        this.transitions = transitions;
        this.machineId = machineId;
        this.autoStartUp = autoStartUp;
    }
}
