package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.TestResultService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.TestResultModel;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

@ActionService("testResultAction")
public class TestResultAction extends AbstractAction {
    public static final Logger LOG = LoggerFactory.getLogger(TestActivityAction.class);


    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private TestResultService testResultService;

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", testResultService.fetchItems(context));
        context.put("count", testResultService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "delete", scope = HttpMethod.DELETE)
    public Map delete(Map context) throws ModelServiceException, ClassNotFoundException {

        final List<Long> pks = (List<Long>) context.get("items");

        for (Long pk : CollectionUtils.emptyIfNull(pks)) {
            final TestResultModel result = (TestResultModel) flexibleSearchService.find(pk, TestResultModel._TYPECODE)
                    .orElse(null);
            for (Object answer : CollectionUtils.emptyIfNull(result.getResponse())) {
                modelService.delete(answer);
            }
            modelService.delete(result);
        }
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
