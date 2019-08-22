package com.statemachinegenerator.smg.domain.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MethodInvoke {

    private Method method;
    private Object object;

}
