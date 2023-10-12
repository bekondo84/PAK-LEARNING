package cm.nzock.mylearning.actions;

import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.enums.InvolveTrainingResult;
import cm.platform.basecommerce.core.enums.TrainingState;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.AbstractActivityModel;
import cm.platform.myleaninig.core.AbstractActivityResultModel;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.TrainingModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.*;

@ActionService("trainingAction")
public class TrainingAction extends AbstractAction {
    public  static final Logger LOG = LoggerFactory.getLogger(TrainingAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private EnumerationService enumService ;

    @Action(value = "save", scope = HttpMethod.POST)
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        TrainingModel item = mapper.readValue(context.get("data") , TrainingModel.class);
        //Object item = gson.fromJson(context.get("data"), type);
        item.setState(enumService.getEnumerationValue(TrainingState.CREATE.getCode(), TrainingState.class));
        getModelService().save(item);
        item = (TrainingModel) getModelService().find(item);
        context.put("data", getModelService().findAndConvertToJson(item));
        return context;
    }

    @Action(value = "PublishTraining", scope = HttpMethod.POST)
    public Map<String, String> publish(Map<String, String> context) throws ModelServiceException, JsonProcessingException {
        final TrainingModel training = mapper.readValue((String) context.get("data") , TrainingModel.class);
        training.setState(enumService.getEnumerationValue(TrainingState.PUBLISH.getCode(), TrainingState.class));
        training.setPublishAt(new Date());
        modelService.save(training);
        context.put("data", mapper.writeValueAsString(training));
        return context ;
    }

    @Action(value = "CloseTraining", scope = HttpMethod.POST)
    public Map<String, String> close(Map<String, String> context) throws ModelServiceException, JsonProcessingException {
        final TrainingModel training = mapper.readValue((String) context.get("data") , TrainingModel.class);
        //Calculer la note de la formation
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("training.code", training.getCode());
        List involves = flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
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
        training.setState(enumService.getEnumerationValue(TrainingState.CLOSE.getCode(), TrainingState.class));
        training.setCloseAt(new Date());
        modelService.save(training);
        context.put("data", mapper.writeValueAsString(training));
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
