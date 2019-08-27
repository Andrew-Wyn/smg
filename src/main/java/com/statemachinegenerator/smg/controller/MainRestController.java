package com.statemachinegenerator.smg.controller;

import com.statemachinegenerator.smg.fsm.StateMachineWrapper;
import com.statemachinegenerator.smg.service.FSMConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainRestController {

    // ---- DEPENDENCIES ----
    private final StateMachineWrapper stateMachineWrapper;
    private final FSMConfigurationService fsmConfigurationService;

    @GetMapping(value = "/")
    public String root(){
        return "finite state machine generator (ONLINE)";
    }

    @GetMapping(value = "/build/{id}")
    public void build(@PathVariable(value = "id") String id) throws Exception{
        stateMachineWrapper.builAndStartFSM(id);
    }

    @GetMapping(value = "/fire/{event}")
    public void fire(@PathVariable(value = "event") String event) throws Exception{
        stateMachineWrapper.fire(event);
    }
}
