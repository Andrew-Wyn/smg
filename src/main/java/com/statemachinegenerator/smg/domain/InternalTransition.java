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

@JsonTypeName("internal")
public class InternalTransition implements Transition {

    @Id
    private String id;

    private String source;
    private long timer;

    public InternalTransition(String source, long timer){
        this.source = source;
        this.timer = timer;
    }

}
