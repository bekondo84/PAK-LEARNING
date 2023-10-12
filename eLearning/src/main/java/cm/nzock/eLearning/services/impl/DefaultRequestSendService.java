package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.beans.RequestSendData;
import cm.nzock.eLearning.services.RequestSendService;
import cm.platform.basecommerce.core.services.BusinessProcessService;
import cm.platform.basecommerce.core.services.ProcessDefinitionService;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.myleaninig.core.RequestSendModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultRequestSendService implements RequestSendService {

    @Autowired
    private ProcessDefinitionService businessProcessService;

    @Override
    public void startRequestProcess(RequestSendData request) throws BusinessProcessException {
        final Map<String, Object> context = new HashMap<>();
        context.put("request", request);
        businessProcessService.process("create-request-send", context);
    }

}
