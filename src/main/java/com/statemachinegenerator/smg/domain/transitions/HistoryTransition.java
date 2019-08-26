package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@JsonTypeName("history")
public class HistoryTransition {

    // necessario per la serializzazione e deserializzazione
    private String type = "history";
    private String source;
    private String target;

}
