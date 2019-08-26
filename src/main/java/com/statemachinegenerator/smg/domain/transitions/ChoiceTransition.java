package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.statemachinegenerator.smg.domain.structures.ChoiceTransitionState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
    TIP: Junction and Choice transition/state have the same behaviour
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeName("choice")
public class ChoiceTransition implements Transition {

    // necessario per la serializzazione e deserializzazione
    private String type = "choice";
    private String source;
    private ChoiceTransitionState first;
    private List<ChoiceTransitionState> thens;
    private String last;

}
