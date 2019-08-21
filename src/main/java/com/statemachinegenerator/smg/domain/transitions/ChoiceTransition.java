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

    @Id
    private String id;

    private String source;
    private ChoiceState first;
    private List<ChoiceState> thens;
    private String last;

    public ChoiceTransition(String source, ChoiceState first,  List<ChoiceState> thens, String last){
        this.source = source;
        this.first = first;
        this.thens = thens;
        this.last = last;
    }

}
