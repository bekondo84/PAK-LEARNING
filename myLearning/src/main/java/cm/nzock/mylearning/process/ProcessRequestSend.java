package cm.nzock.mylearning.process;

import cm.platform.basecommerce.core.enums.RequestSendState;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.RequestSendModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component("processRequestSend")
public class ProcessRequestSend implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessRequestSend.class);

    @Autowired
    private EnumerationService enumerationService;
    @Autowired
    private ModelService modelService;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
       final RequestSendModel request = (RequestSendModel) context.get("request");
       assert Objects.nonNull(request) : "No Request found ....";
       assert StringUtils.isNotBlank(request.getResponse()) : String.format("Request response  %s is empty ...", request.getCode());
       request.setState(enumerationService.getEnumerationValue(RequestSendState.CLOSE.getCode(), RequestSendState.class));
        try {
            modelService.save(request);

            return  "OK" ;
        } catch (ModelServiceException e) {
            LOG.error(String.format("Something went wrong ...", e));
        }
        return "KO";
    }
}
