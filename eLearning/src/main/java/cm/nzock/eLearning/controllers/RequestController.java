package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.beans.RequestSendData;
import cm.nzock.eLearning.services.RequestSendService;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(IsisConstants.ApiScope.API+"/request-send")
public class RequestController {

    @Autowired
    private RequestSendService requestSendService;

    @PostMapping("/process-request")
    public void processRequest(@RequestBody RequestSendData request) throws BusinessProcessException {
        assert Objects.nonNull(request) : "Request can't be null";
        requestSendService.startRequestProcess(request);
    }
}
