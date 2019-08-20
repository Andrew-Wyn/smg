package com.statemachinegenerator.smg.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
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
    private String first;
    private List<String> then;
    private String last;

    public ChoiceTransition(String source, String first, List<String> then, String last){
        this.source = source;
        this.first = first;
        this.last = last;
    }

}
