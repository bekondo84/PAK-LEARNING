package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.services.ActivityService;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.core.services.ProcessDefinitionService;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.AbstractActivityModel;
import cm.platform.myleaninig.core.AbstractActivityResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultActivityService implements ActivityService {
    public static final Logger LOG = LoggerFactory.getLogger(DefaultActivityService.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @Override
    public void startActivity(String code, String username) throws BusinessProcessException {
         final Object activity =  flexibleSearchService.find(code, "code", AbstractActivityModel._TYPECODE).orElse(null);
         final Map<String, Object> context = new HashMap<>();
         //LOG.info(String.format("------Activity found : %s", activity));
         context.put("activity", activity);
         context.put("username", username);
         processDefinitionService.process("create-activity-result", context);
    }

    @Override
    public long nbreOfActivitiesComplete(Long concern, Long training) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.pk", concern);
        container.addEq("training.pk", training);
        container.addEq("state.code", TestResultState.DONE.getCode());
        return flexibleSearchService.count(AbstractActivityResultModel.class, container);
    }


}
