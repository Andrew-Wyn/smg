package com.statemachinegenerator.smg.controller.crud;

import com.bmeme.lib.error.message.ResponseContainer;
import com.bmeme.lib.error.message.ResponseMessage;
import com.bmeme.lib.rest.domain.utils.FilterBean;
import com.bmeme.lib.rest.resolver.ExtendedSearchParam;
import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.service.FSMConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/configuration")
public class FSMConfgurationController {

    private final FSMConfigurationService service;

    // ---- GET ----

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage getAllQuiz(
            @PageableDefault(size = 20) Pageable pageable,
            @ExtendedSearchParam FilterBean filterBean) {
        return service.getAllWrapped(filterBean, pageable);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage getAllQuestionNotPaged(
            @ExtendedSearchParam FilterBean filterBean) {
        return  service.getAllWrapped(filterBean);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage getQuizById(@PathVariable(name = "id") String id){
        return service.getWrapped(id);
    }

    // ---- POST ----

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseContainer> insertMultipleQuiz(@RequestBody ArrayList<FSMConfiguration> fsmConfigurations){
        return service.createAll(fsmConfigurations);
    }

    // ---- PUT ----

    @PutMapping(value="{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage updateQuiz(@PathVariable(name = "id") String id, @RequestBody FSMConfiguration fsmConfiguration) {
        return service.updateWrapped(id, fsmConfiguration);
    }


    // ---- DELETE ----

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseContainer> deleteQuizById(@PathVariable(name = "id") List<String> id){
        return service.delete(id);
    }

    // ---- PATCH ----

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage patchQuiz(@PathVariable(name = "id") String id, @RequestBody Map<String, Object> fields) {
        return service.patchWrapped(id, fields);
    }

}
