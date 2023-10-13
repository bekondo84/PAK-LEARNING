package cm.nzock.eLearning.facades.impl;

import cm.nzock.eLearning.beans.InvolveData;
import cm.nzock.eLearning.beans.TrainingData;
import cm.nzock.eLearning.beans.WorkgroupActivityData;
import cm.nzock.eLearning.converters.InvolveDataConverter;
import cm.nzock.eLearning.converters.TrainingDataConverter;
import cm.nzock.eLearning.facades.TrainingFacade;
import cm.nzock.eLearning.populators.WorkgroupActivityPopulator;
import cm.nzock.eLearning.services.TrainingService;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.ParticipantModel;
import cm.platform.myleaninig.core.TrainingModel;
import cm.platform.myleaninig.core.WorkGroupActivityModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DefaultTrainingFacade implements TrainingFacade {

    @Autowired
    private TrainingService trainingService ;
    @Autowired
    private InvolveDataConverter converter ;
    @Autowired
    private TrainingDataConverter trainingConverter;
    @Autowired
    private WorkgroupActivityPopulator workgroupActivityPopulator;
    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<InvolveData> getTrainingToRegisterForUsername(String username) {
        final List<InvolveModel> involves = trainingService.getTrainingToRegisterForUsername(username);
        return CollectionUtils.emptyIfNull(involves)
                .stream()
                .map(trainig -> converter.convert(trainig))
                .collect(Collectors.toList());
    }

    @Override
    public long countTrainingToRegisterForUsername(String username) {
        return trainingService.countTrainingToRegisterForUsername(username);
    }

    @Override
    public List<WorkgroupActivityData> getWorkgroupForTraining(String training) {
        final List<WorkGroupActivityModel> groupes = trainingService.getWorkgroupForTraining(training);
        return CollectionUtils.emptyIfNull(groupes)
                .stream()
                .filter(grp -> grp.getVirtual() || Objects.nonNull(grp.getReserve()) && Objects.nonNull(grp.getSize()) && (grp.getReserve() <=grp.getSize()))
                .map(grp ->{
                    final WorkgroupActivityData data = new WorkgroupActivityData();
                    workgroupActivityPopulator.populate(grp, data);
                    return data;
                }).collect(Collectors.toList());
    }

    @Override
    public void registerForTraining(Long involve, String group) throws ModelServiceException {
         trainingService.registerForTraining(involve, group);
    }

    @Override
    public void startTraining(Long involve) throws ModelServiceException {
        trainingService.startTraining(involve);
    }

    @Override
    public long countRegisterTraining(String username) {
        return trainingService.countRegisterTraining(username);
    }

    @Override
    public List<InvolveData> getRegisterTraining(String username) {
        final List<InvolveModel> involves = trainingService.getRegisterTraining(username);
        return CollectionUtils.emptyIfNull(involves)
                .stream()
                .map(trainig -> converter.convert(trainig))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvolveData> getDoneTrainings(String username) {
        final List<InvolveModel> involves = trainingService.getDoneTrainings(username);
        return CollectionUtils.emptyIfNull(involves)
                .stream()
                .map(trainig -> converter.convert(trainig))
                .collect(Collectors.toList());
    }

    @Override
    public void unregisterForTraining(Long involvePk, String group) throws ModelServiceException {
        trainingService.unregisterForTraining(involvePk, group);
    }

    @Override
    public TrainingData getTraining(String code) {
        final TrainingModel training = trainingService.getTraining(code);
        if (Objects.nonNull(training)) {
            return  trainingConverter.convert(training);
        }
        throw new NzockException(String.format("No training found with code %s", code));
    }

    @Override
    public Set<WorkgroupActivityData> getOnlineTraining(final String username) {
        final ParticipantModel participant = (ParticipantModel) flexibleSearchService.find(username, "code", ParticipantModel._TYPECODE).orElse(null);
        return  CollectionUtils.emptyIfNull(trainingService.getOnlineTraining(getParticipantsGroup(participant), username))
                .stream().map(wg -> {
                    final WorkgroupActivityData workgroupActivityData =  new WorkgroupActivityData();
                    workgroupActivityPopulator.populate(wg, workgroupActivityData);
                    return  workgroupActivityData;
                }).collect(Collectors.toSet());
    }

    @Override
    public InvolveData subscribeOnlineTraining(String code, String username) throws ModelServiceException {
        return converter.convert(trainingService.registerOnlineTraining(code, username));
    }

    private List<String> getParticipantsGroup(ParticipantModel participant) {
        return CollectionUtils.emptyIfNull(participant.getGroupes())
                .stream().map(grp -> grp.getCode())
                .collect(Collectors.toList());
    }
}
