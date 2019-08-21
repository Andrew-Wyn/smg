package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.statemachinegenerator.smg.domain.transitions.ChoiceTransition;
import com.statemachinegenerator.smg.domain.transitions.ExternalTransition;
import com.statemachinegenerator.smg.domain.transitions.InternalTransition;

/*
    non posso utilizzare un interfaccia, perche alza un eccezione a livello di deserializzazione
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExternalTransition.class, name = "external"),
        @JsonSubTypes.Type(value = InternalTransition.class, name = "internal"),
        @JsonSubTypes.Type(value = ChoiceTransition.class, name = "choice")
})
public interface Transition {}
