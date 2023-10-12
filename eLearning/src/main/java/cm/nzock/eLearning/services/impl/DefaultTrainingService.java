package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.services.TrainingService;
import cm.platform.basecommerce.core.enums.InvolveState;
import cm.platform.basecommerce.core.enums.InvolveType;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.core.enums.TrainingState;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultTrainingService implements TrainingService {
    public static final Logger LOG = LoggerFactory.getLogger(DefaultTrainingService.class);

    public static final int PERCENT = 100;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private EnumerationService enumService ;

    @Override
    public List<WorkGroupActivityModel> getOnlineTraining(List<String> groupes) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("online", Boolean.TRUE);
        parameters.put("groupes", groupes);
        parameters.put("publishState", TrainingState.PUBLISH.getCode());
        return flexibleSearchService.doSearch(TrainingService.GET_ONLINE_TRAINING, parameters);
    }

    @Override
    public List<InvolveModel> getTrainingToRegisterForUsername(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        //container.addEq("register", Boolean.FALSE);
        container.addEq("state.code", InvolveState.REGISTER.getCode());
        container.addEq("type.code", InvolveType.STUDENT.getCode());
        container.addEq("training.state.code", TrainingState.PUBLISH.getCode());
        return flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
    }

    @Override
    public long countDoneTraining(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        container.addEq("register", Boolean.TRUE);
        container.addEq("state.code", InvolveState.FINISH.getCode());
        container.addEq("type.code", InvolveType.STUDENT.getCode());
         return flexibleSearchService.count(InvolveModel.class, container);
    }

    @Override
    public long countOnlineTraining(final String username) {
        final ParticipantModel participant = (ParticipantModel) flexibleSearchService.find(username, "code", ParticipantModel._TYPECODE).orElse(null);
        final List<WorkGroupActivityModel> items = getOnlineTraining(getParticipantsGroup(participant));
        return CollectionUtils.emptyIfNull(items).stream()
                .map(item -> item.getCode())
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public long countTrainingToRegisterForUsername(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        //container.addEq("register", Boolean.FALSE);
        container.addEq("state.code", InvolveState.REGISTER.getCode());
        container.addEq("type.code", InvolveType.STUDENT.getCode());
        container.addEq("training.state.code", TrainingState.PUBLISH.getCode());
        return flexibleSearchService.count(InvolveModel.class, container);
    }

    @Override
    public List<WorkGroupActivityModel> getWorkgroupForTraining(String training) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("training.code", training);
        return flexibleSearchService.doSearch(WorkGroupActivityModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
    }

    @Override
    public void registerForTraining(Long involvePk, String groupCode) throws ModelServiceException {
        final InvolveModel involve = (InvolveModel) flexibleSearchService.find(involvePk, InvolveModel._TYPECODE)
                                                                          .orElse(null);
        final WorkGroupActivityModel group = (WorkGroupActivityModel) flexibleSearchService.find(groupCode, "code", WorkGroupActivityModel._TYPECODE).orElse(null);

        if (Objects.nonNull(involve) && Objects.nonNull(group)) {
            involve.setWorkgroup(group);
            involve.setRegisterDate(new Date());
            involve.setRegister(Boolean.TRUE);
            involve.setState(enumService.getEnumerationValue(InvolveState.REGISTER.getCode(), InvolveState.class));
            modelService.save(involve);
            //Update group register
            group.setReserve(group.getReserve());
        }
    }

    @Override
    public void startTraining(Long involvePk) throws ModelServiceException {
        final InvolveModel involve = (InvolveModel) flexibleSearchService.find(involvePk, InvolveModel._TYPECODE)
                .orElse(null);
        if (Objects.nonNull(involve)) {
            involve.setState(enumService.getEnumerationValue(InvolveState.INPROGRESS.getCode(), InvolveState.class));
            involve.setStartingDate(new Date());
            modelService.save(involve);
        }
    }

    @Override
    public void markTrainingAsFinish(Long concern, Long training) throws ModelServiceException {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.pk", concern);
        container.addEq("training.pk", training);
        List involves = flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
        final InvolveModel involve = CollectionUtils.isEmpty(involves) ? null : (InvolveModel) involves.get(0);
        if (Objects.isNull(involve)) {
            LOG.info(String.format(":::::: Involve is Null ::::::"));
            return;
        }
        if (Objects.isNull(involve.getTraining()) || Objects.isNull(involve.getConcern())) {
            LOG.info(String.format("::::: Involve Training or Concern is Null ::::::"));
            return;
        }
        container = RestrictionsContainer.newInstance();
        container.addEq("training.pk", involve.getTraining().getPK());
        container.addEq("concern.pk", involve.getConcern().getPK());
        final List activitiesResult = flexibleSearchService.doSearch(AbstractActivityResultModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
       LOG.info(String.format("Fecth ActivitiesResults :::::::: %s", CollectionUtils.emptyIfNull(activitiesResult).size()));
        if (checkIfAllActivitiesResultAsNotDone(activitiesResult)
           || checkIfTrainingActivitiesComplete(involve, activitiesResult)){
            LOG.info(String.format("::::: Some Activities are not yet DONE checkIfAllActivitiesResultAsDone : %s :::::: checkIfTrainingActivitiesComplete : %s", checkIfAllActivitiesResultAsNotDone(activitiesResult), checkIfTrainingActivitiesComplete(involve, activitiesResult)));
            return;
        }

        involve.setState(enumService.getEnumerationValue(InvolveState.FINISH.getCode(), InvolveState.class));
        involve.setFinishedDate(new Date());
        modelService.save(involve);
    }

    private boolean checkIfTrainingActivitiesComplete(InvolveModel involve, List activitiesResult) {
          RestrictionsContainer container = RestrictionsContainer.newInstance();
          container.addEq("training.pk", involve.getTraining().getPK());
          List activities = flexibleSearchService.doSearch(AbstractActivityModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
          return CollectionUtils.emptyIfNull(CollectionUtils.emptyIfNull(activities)).size() > CollectionUtils.emptyIfNull(activitiesResult).size();
    }

    private boolean checkIfAllActivitiesResultAsNotDone(List activitiesResult) {
        return CollectionUtils.emptyIfNull(activitiesResult)
                .stream()
                .anyMatch(obj -> Objects.nonNull(obj)
                                && (Objects.isNull(((AbstractActivityResultModel)obj).getState())
                        || !((AbstractActivityResultModel)obj).getState().getCode().equals(TestResultState.DONE.getCode())));
    }

    @Override
    public void unregisterForTraining(Long involvePk, String group) throws ModelServiceException {
        final InvolveModel involve = (InvolveModel) flexibleSearchService.find(involvePk, InvolveModel._TYPECODE)
                .orElse(null);
        if (Objects.nonNull(involve) && Objects.nonNull(group)) {
            involve.setWorkgroup(null);
            involve.setRegisterDate(null);
            involve.setRegister(Boolean.FALSE);
            modelService.save(involve);
        }
    }

    @Transactional
    @Override
    public InvolveModel registerOnlineTraining(String groupe, String username) throws ModelServiceException {
        final ParticipantModel participant = (ParticipantModel) flexibleSearchService.find(username, "code", ParticipantModel._TYPECODE).orElse(null);
        final WorkGroupActivityModel worgroup = (WorkGroupActivityModel) flexibleSearchService.find(groupe, "code", WorkGroupActivityModel._TYPECODE).orElse(null);

        if (Objects.nonNull(participant) && Objects.nonNull(worgroup)) {
            //find involve
            RestrictionsContainer container = RestrictionsContainer.newInstance();
            container.addEq("concern.code", participant.getCode());
            container.addEq("workgroup.code", worgroup.getCode());
            List involves = flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1) ;
            InvolveModel involve  = new InvolveModel();
            if (CollectionUtils.isEmpty(involves)) {
                involve.setTraining(worgroup.getTraining());
                involve.setConcern(participant);
                involve.setRegisterDate(new Date());
                involve.setRegister(Boolean.TRUE);
                involve.setState(enumService.getEnumerationValue(InvolveState.REGISTER.getCode(), InvolveState.class));
                involve.setWorkgroup(worgroup);
                involve.setType(enumService.getEnumerationValue(InvolveType.STUDENT.getCode(), InvolveType.class));
                modelService.getEm().persist(involve);
                return involve;
            } else {
                involve   = (InvolveModel) involves.get(0);
            }
            if (Objects.isNull(involve.getRegisterDate())) {
                involve.setRegisterDate(new Date());
            }
            if (Objects.isNull(involve.getRegister())) {
                involve.setRegister(Boolean.TRUE);
            }
            if (Objects.isNull(involve.getState())) {
                involve.setState(enumService.getEnumerationValue(InvolveState.REGISTER.getCode(), InvolveState.class));
            }
            modelService.save(involve);
            return involve;
        }
       throw new NzockException(String.format("Enable to register to this training Concern or Workgroup is null"));
    }

    @Override
    public long countRegisterTraining(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        container.addEq("register", Boolean.TRUE);
        container.addEq("state.code", InvolveState.INPROGRESS.getCode());
        container.addEq("type.code", InvolveType.STUDENT.getCode());
        container.addEq("training.state.code", TrainingState.PUBLISH.getCode());
        return flexibleSearchService.count(InvolveModel.class, container);
    }

    @Override
    public List<InvolveModel> getRegisterTraining(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        container.addEq("register", Boolean.TRUE);
        container.addEq("state.code", InvolveState.INPROGRESS.getCode());
        container.addEq("type.code", InvolveType.STUDENT.getCode());
        container.addEq("training.state.code", TrainingState.PUBLISH.getCode());
        return flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
    }

    @Override
    public List<InvolveModel> getDoneTrainings(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        container.addEq("register", Boolean.TRUE);
        container.addEq("state.code", InvolveState.FINISH.getCode());
        container.addEq("type.code", InvolveType.STUDENT.getCode());
        return flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
    }

    @Override
    public TrainingModel getTraining(String code) {
        return (TrainingModel) flexibleSearchService.find(code, "code", TrainingModel._TYPECODE).orElse(null);
    }

    @Override
    public Float getTrainingSkill(String username, String code) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", username);
        container.addEq("training.code", code);
        container.addEq("state.code", TestResultState.DONE.getCode());
        long close  = flexibleSearchService.count(AbstractActivityResultModel.class, container);
        container  = RestrictionsContainer.newInstance();
        container.addEq("training.code", code);
        container.addEq("online", Boolean.TRUE);
        long totalActivites  = flexibleSearchService.count(AbstractActivityModel.class, container);
        if (close <= totalActivites) {
            return PERCENT *close /(totalActivites > 0 ? totalActivites : 1.0f);
        } else {
            return  Float.valueOf(PERCENT);
        }
    }

    private List<String> getParticipantsGroup(ParticipantModel participant) {
        return CollectionUtils.emptyIfNull(participant.getGroupes())
                .stream().map(grp -> grp.getCode())
                .collect(Collectors.toList());
    }
}
