package com.statemachinegenerator.smg.domain.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Region {

    private String parent;
    private String initial;
    private String end;
    private List<State> states;

}
