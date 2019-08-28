package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeName("external")
public class ExternalTransition implements Transition {

    private String source;
    private String target;
    private String event;
    private String action;
    private String errorAction;
    private String guard;

}
