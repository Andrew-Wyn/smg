package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeName("fork")
public class ForkTransition {

    // necessario per la serializzazione e deserializzazione
    private String type = "fork";
    private String source;
    private List<String> targets;

}
