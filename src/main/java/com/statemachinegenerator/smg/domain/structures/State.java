package com.statemachinegenerator.smg.domain.structures;

import com.statemachinegenerator.smg.domain.enums.StateTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class State {

    private String value;
    private StateTypes type;

}
