package com.statemachinegenerator.smg.domain.transitions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.statemachinegenerator.smg.domain.structures.ChoiceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeName("choice")
public class ChoiceTransition implements Transition {

    private String source;
    private ChoiceState first;
    private List<ChoiceState> thens;
    private String last;

}
