package com.statemachinegenerator.smg.service;

import com.bmeme.lib.error.exception.BaseException;
import com.bmeme.lib.error.message.ResponseContainer;
import com.bmeme.lib.error.message.ResponseMessage;
import com.bmeme.lib.rest.domain.utils.FilterBean;
import com.bmeme.lib.rest.factory.mongo.GenericService;
import com.bmeme.lib.rest.service.PatchService;
import com.statemachinegenerator.smg.domain.FSMConfiguration;
import com.statemachinegenerator.smg.repository.FSMConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FSMConfigurationService extends GenericService<FSMConfiguration, String, FSMConfigurationRepository> {

    public FSMConfigurationService(FSMConfigurationRepository repository, PatchService patchService, MongoTemplate mongoTemplate) {
        super(repository, patchService, mongoTemplate, FSMConfiguration.class);
    }

    public ResponseEntity<ResponseContainer> createAll(List<FSMConfiguration> fsmConfigurations){
        ResponseContainer responseContainer = new ResponseContainer();

        for(FSMConfiguration fsmConfiguration : fsmConfigurations){
            try {
                responseContainer.addResponseMessage(new ResponseMessage(HttpStatus.OK, super.createElement(fsmConfiguration)));
            } catch(BaseException e) {
                responseContainer.addResponseMessage(new ResponseMessage(e.getHttpStatus(), e));
            } catch (Exception e) {
                BaseException baseException = new BaseException(e);
                baseException.printStackTrace();
                responseContainer.addResponseMessage(new ResponseMessage(baseException.getHttpStatus(), baseException));
            }
        }
        return responseContainer.getResponse();
    }

    // services wrapped for get only the Responsemessage not the entire list

    public ResponseMessage getAllWrapped(FilterBean filterBean, Pageable page){
        ResponseContainer response = super.getAll(filterBean, page).getBody();
        return (response != null) ? response.getResponses().get(0) : null;
    }

    public ResponseMessage getAllWrapped(FilterBean filterBean){
        ResponseContainer response = super.getAll(filterBean).getBody();
        return (response != null) ? response.getResponses().get(0) : null;
    }

    public ResponseMessage getWrapped(String id){
        ResponseContainer response = super.get(id).getBody();
        return (response != null) ? response.getResponses().get(0) : null;
    }

    public ResponseMessage updateWrapped(String id, FSMConfiguration fsmConfiguration){
        ResponseContainer response = super.update(id, fsmConfiguration).getBody();
        return (response != null) ? response.getResponses().get(0) : null;
    }

    public ResponseMessage patchWrapped(String id, Map<String,Object> fields){
        ResponseContainer response = super.patch(id, fields).getBody();
        return (response != null) ? response.getResponses().get(0) : null;
    }

    // --------------------------------

}
