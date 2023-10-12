package cm.nzock.eLearning.process;

import cm.nzock.eLearning.beans.RequestSendData;
import cm.nzock.eLearning.populators.RequestSendPopulator;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
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

@Component("createRequestSend")
public class CreateRequestSend implements Action {
  private static Logger LOG = LoggerFactory.getLogger(CreateRequestSend.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private RequestSendPopulator populator;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        final RequestSendData requestSendData = (RequestSendData) context.get("request");

        assert Objects.nonNull(requestSendData): String.format("No request found ....");
        assert StringUtils.isNotBlank(requestSendData.getMessage()): String.format("The message body is empty");
        assert  StringUtils.isNotBlank(requestSendData.getSender()) : "The message sender is empty";

        final RequestSendModel request = new RequestSendModel();

        populator.reversePopulate(requestSendData, request);

        try {
            modelService.save(request);
            context.put("request", request);
            return "OK";
        } catch (ModelServiceException e) {
           LOG.error(String.format("Something went wrong ....", e));
        }
        return "KO";
    }
}
