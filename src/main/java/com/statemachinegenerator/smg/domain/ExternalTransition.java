package com.statemachinegenerator.smg.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonTypeName("external")
public class ExternalTransition implements Transition {

    @Id
    private String id;

    //private String type;
    private String source;
    private String target;
    private String event;

    public ExternalTransition(String source, String target, String event){
        this.source = source;
        this.target = target;
        this.event = event;
    }

}
