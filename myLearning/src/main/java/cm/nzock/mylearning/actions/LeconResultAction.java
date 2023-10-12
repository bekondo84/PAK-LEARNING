package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.LeconResultService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.LeconEvaluationModel;
import cm.platform.myleaninig.core.LeconResultModel;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.*;

@ActionService("leconResultAction")
public class LeconResultAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(LeconResultAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private LeconResultService leconResultService;

    @Action(value = "delete", scope = HttpMethod.DELETE)
    public Map delete(Map context) throws ModelServiceException, ClassNotFoundException {
        final List<Long> pks = (List<Long>) context.get("items");
        //Delete Involve
        deleteInvolve(pks);
        for (Long pk : CollectionUtils.emptyIfNull(pks)) {
            final LeconResultModel leconResult = (LeconResultModel) flexibleSearchService.find(pk, LeconResultModel._TYPECODE)
                    .orElse(null);
            if (Objects.nonNull(leconResult)) {
                //Delete Evaluations
                for (LeconEvaluationModel evaluation : CollectionUtils.emptyIfNull(leconResult.getEvaluations())) {
                      //Delete Question
                    for (Object answer : CollectionUtils.emptyIfNull(evaluation.getResponse())) {
                        modelService.delete(answer);
                    }
                    modelService.delete(evaluation);
                 }
                modelService.delete(leconResult);
            }
        }
        return context;
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", leconResultService.fetchItems(context));
        context.put("count", leconResultService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "resultCount", scope = HttpMethod.GET)
    public Map resultCount(Map context) throws ClassNotFoundException {
        context.put("type", LeconResultModel.class.getTypeName());
        context.put("count", leconResultService.nbreOfFetchItems(context));
        return context;
    }

    private void deleteInvolve(List<Long> pks) throws ModelServiceException {
        if (CollectionUtils.isNotEmpty(pks)) {
            final LeconResultModel leconResult = (LeconResultModel) flexibleSearchService.find(pks.get(0), LeconResultModel._TYPECODE)
                    .orElse(null);
            if (Objects.nonNull(leconResult)) {
                RestrictionsContainer container = RestrictionsContainer.newInstance();
                container.addEq("concern.code", leconResult.getConcern().getCode());
                container.addEq("training.code", leconResult.getTraining().getCode());
                List involves = flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
                InvolveModel involve = CollectionUtils.isEmpty(involves) ? null : (InvolveModel) involves.get(0);
                if (Objects.nonNull(involve)) {
                    modelService.delete(involve);
                }
            }
        }
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
