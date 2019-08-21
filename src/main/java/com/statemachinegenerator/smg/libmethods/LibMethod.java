package com.statemachinegenerator.smg.libmethods;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE}) // tipo di struttura sulla quale è applicabile
@Retention(RetentionPolicy.RUNTIME) // disponibile a runtime
@Documented
@Indexed
@Component
public @interface LibMethod {
}
