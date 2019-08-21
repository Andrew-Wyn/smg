package com.statemachinegenerator.smg.domain.transitions;

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

    private String source;
    private String target;
    private String event;
    private String action;
    private String errorAction;

    public ExternalTransition(String source, String target, String event, String action, String errorAction){
        this.source = source;
        this.target = target;
        this.event = event;
        this.action = action;
        this.errorAction = errorAction;
    }

}
