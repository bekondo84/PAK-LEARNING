package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.TestActivityService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.TestActivityModel;
import cm.platform.myleaninig.core.TrainingModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ActionService("testActivityAction")
public class TestActivityAction extends AbstractAction {

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private TestActivityService testActivityService;

    @Override
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        TestActivityModel item = (TestActivityModel) mapper.readValue(context.get("data") , TestActivityModel.class);
        item.setOnline(Boolean.TRUE);
        Integer parent = StringUtils.isNoneBlank(context.get("parent")) ? Integer.valueOf(context.get("parent")) : null ;
        if (Objects.nonNull(parent)) {
            TrainingModel training = (TrainingModel) flexibleSearchService.find(parent.longValue(), TrainingModel._TYPECODE).orElse(null);
            if (Objects.nonNull(training)) {
                item.setTraining(training);
            }
        }
        getModelService().save(item);
        item = (TestActivityModel) getModelService().find(item);
        context.put("data", getModelService().findAndConvertToJson(item));
        return context;
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", testActivityService.fetchItems(context));
        context.put("count", testActivityService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "testActivitiesCount", scope = HttpMethod.GET)
    public Map testActivitiesCount(Map context) throws ClassNotFoundException {
        context.put("type", TestActivityModel.class.getTypeName());
        context.put("count", testActivityService.nbreOfFetchItems(context));
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
