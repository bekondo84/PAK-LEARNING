package cm.nzock.mylearning.actions;

import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.Map;

@ActionService("openQuestionAction")
public class OpenQuestionAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(OpenQuestionAction.class);

    @Autowired
    private ModelService modelService ;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;


    @Action(value = "uploadOpenQuestion", scope = HttpMethod.POST)
    public Map upload(final Map context) {
       LOG.info("INSIDE OpenQuestion -----------------");
        return context ;
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
