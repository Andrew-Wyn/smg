package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.statemachinegenerator.smg.domain.structures.ChoiceTransitionState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeName("choice")
public class ChoiceTransition implements Transition {

    private String source;
    private ChoiceTransitionState first;
    private List<ChoiceTransitionState> thens;
    private String last;

}
