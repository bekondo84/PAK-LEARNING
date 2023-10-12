package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.impl.DefaultWorkGroupService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.enums.InvolveTrainingResult;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.*;

import static cm.platform.basecommerce.core.utils.IsisConstants.ActionsKeys.ITEMTYPEKEY;
import static cm.platform.basecommerce.core.utils.IsisConstants.ActionsKeys.NUMBEROFITEMS;

@ActionService("workGroupAction")
public class WorkGroupAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(WorkGroupAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ObjectMapper mapper ;
    @Autowired
    private DefaultWorkGroupService workGroupService;
    @Autowired
    private EnumerationService enumService ;

    @Override
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        final TrainingModel training = getTraining(context);
        final WorkGroupActivityModel workgroup = getWorkGroupActivity(context);
        workgroup.setTraining(training);
        modelService.save(workgroup);

        //Process Involve
        for (InvolveModel involve : CollectionUtils.emptyIfNull(workgroup.getMembers())) {
            involve.setWorkgroup(workgroup);
            involve.setTraining(training);
            if (Objects.nonNull(involve.getResult())) {
                involve.setResult(enumService.getEnumerationValue(involve.getResult().getCode(), InvolveTrainingResult.class));
            }
            modelService.save(involve);
        }
        context.put("data", modelService.findAndConvertToJson(workgroup));
        return context;
    }

    private WorkGroupActivityModel getWorkGroupActivity(Map<String, String> context) throws JsonProcessingException {
        final WorkGroupActivityModel workgroup = mapper.readValue(context.get("data"), WorkGroupActivityModel.class);
        if (Objects.isNull(workgroup)) {
            throw new NzockException("No WorgroupActivity found in the datastream");
        }
        return workgroup;
    }

    private TrainingModel getTraining(Map<String, String> context) throws ModelServiceException {
        String parentId = context.get("parent");
        final TrainingModel training = (TrainingModel) flexibleSearchService.find(Long.valueOf(parentId), TrainingModel._TYPECODE).orElse(null);
        if (Objects.isNull(training)) {
            throw new NzockException(String.format("No training found with PK : %s", parentId));
        }
        return training;
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", workGroupService.fetchItems(context));
        context.put("count", workGroupService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "workgroupCount", scope = HttpMethod.GET)
    public Map workgroupCount(final Map context) throws ClassNotFoundException {
        context.put(ITEMTYPEKEY, WorkGroupActivityModel.class.getTypeName());
        context.put(NUMBEROFITEMS, workGroupService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "validateWorkgroup", scope = HttpMethod.POST)
    public Map closeWorkgroup(Map context) throws ModelServiceException, JsonProcessingException {
        final WorkGroupActivityModel workGroup = getWorkGroupActivity(context);
        final TrainingModel training = workGroup.getTraining();
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("workgroup.code", workGroup.getCode());
        container.addEq("training.code", training.getCode());
        final List involves = flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);

        for (Object obj : CollectionUtils.emptyIfNull(involves)) {
            InvolveModel involve = (InvolveModel) obj;
            container = RestrictionsContainer.newInstance();
            container.addEq("concern.code", involve.getConcern().getCode());
            container.addEq("training.code", training.getCode());
            List results = flexibleSearchService.doSearch(AbstractActivityResultModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
            Float mark = 0.0f ;
            for (Object obj1 : CollectionUtils.emptyIfNull(results)) {
                AbstractActivityResultModel result = (AbstractActivityResultModel) obj1;
                if (Objects.nonNull(result.getMark()) && Objects.nonNull(result.getPercent())) {
                    mark += result.getMark() * result.getPercent()/100;
                }
            }
            involve.setMark(mark);
            //Compute the minimum for success
            container = RestrictionsContainer.newInstance();
            container.addEq("training.code", training.getCode());
            List activities = flexibleSearchService.doSearch(AbstractActivityModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
            Float successAt = 0.0f;
            for (Object activ : activities) {
                if (Objects.nonNull(((AbstractActivityModel)activ).getSuccessAt())
                        && Objects.nonNull(((AbstractActivityModel)activ).getPercent())) {
                    successAt += ((AbstractActivityModel)activ).getSuccessAt()*((AbstractActivityModel)activ).getPercent()/100;
                }
            }
            if (mark >= successAt){
                involve.setResult(enumService.getEnumerationValue(InvolveTrainingResult.SUCCESSED.getCode(), InvolveTrainingResult.class));
            } else {
                involve.setResult(enumService.getEnumerationValue(InvolveTrainingResult.FAILED.getCode(), InvolveTrainingResult.class));
            }
            modelService.save(involve);
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
