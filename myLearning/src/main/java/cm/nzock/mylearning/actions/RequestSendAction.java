package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.RequestSendService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.services.ProcessDefinitionService;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.myleaninig.core.RequestSendModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

@ActionService("requestSendAction")
public class RequestSendAction extends AbstractAction {
    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ProcessDefinitionService processDefinitionService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RequestSendService requestSendService;

    @Action(value = "markAsProcess", scope = HttpMethod.POST)
    public Map<String, String> markAsProcess(final Map<String, String> context) throws JsonProcessingException, BusinessProcessException {
        final RequestSendModel request = mapper.readValue(context.get("data"), RequestSendModel.class);

        Map<String, Object> requestContext = new HashMap<>();
        requestContext.put("request", request);
        processDefinitionService.process("request-send-process", requestContext);

        return context;
    }

    @Action(value = "requestToprocessCount", scope = HttpMethod.GET)
    public Map requestToprocessCount(Map context) throws ClassNotFoundException {
        context.put("count", requestSendService.countRequestToProcess(context));
        return context;
    }

    @Override
    protected ModelService getModelService() {
        return modelService;
    }

    @Override
    protected FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    @Override
    protected MetaTypeService getMetaTypeService() {
        return metaTypeService;
    }
}
