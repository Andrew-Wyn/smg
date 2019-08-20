package com.statemachinegenerator.smg.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
