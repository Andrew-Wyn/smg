package com.statemachinegenerator.smg.domain.structures;

import com.statemachinegenerator.smg.domain.enums.HistoryTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryState {

    private String value;
    private HistoryTypes state;

}
