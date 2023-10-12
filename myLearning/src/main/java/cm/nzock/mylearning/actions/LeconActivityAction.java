package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.LeconActivityService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.LeconActivityModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.Map;

@ActionService("leconActivityAction")
public class LeconActivityAction extends AbstractAction  {

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private LeconActivityService leconActivityService;


    @Override
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        return leconActivityService.create(context);
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", leconActivityService.fetchItems(context));
        context.put("count", leconActivityService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "leconsCount", scope = HttpMethod.GET)
    public Map leconsCount(Map context) throws ClassNotFoundException {
        context.put("type", LeconActivityModel.class.getTypeName());
        context.put("count", leconActivityService.nbreOfFetchItems(context));
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
