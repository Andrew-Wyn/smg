package com.statemachinegenerator.smg.domain.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChoiceTransitionState {

    private String value;
    private String guard;
    private String action;

}
