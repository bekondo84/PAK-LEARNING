package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.HomeworkActivityService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.HomeworkActivityModel;
import cm.platform.myleaninig.core.InvolveModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Map;

@ActionService("homeworkActivityAction")
public class HomeworkActivityAction extends AbstractAction  {

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private HomeworkActivityService homeworkActivityService;


    @Override
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        return homeworkActivityService.create(context);
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", homeworkActivityService.fetchItems(context));
        context.put("count", homeworkActivityService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "homeActivitiesCount", scope = HttpMethod.GET)
    public Map workgroupCount(Map context) throws ClassNotFoundException {
        context.put("type", HomeworkActivityModel.class.getTypeName());
        context.put("count", homeworkActivityService.nbreOfFetchItems(context));
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
