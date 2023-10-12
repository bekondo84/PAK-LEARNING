package cm.nzock.eLearning.services;

import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.TrainingModel;
import cm.platform.myleaninig.core.WorkGroupActivityModel;

import java.util.List;

public interface TrainingService {
    public static final String GET_ONLINE_TRAINING = "SELECT  c FROM WorkGroupActivityModel AS c JOIN c.groupes AS g  WHERE c.virtual = :online AND c.training.state.code = :publishState  AND g.code IN :groupes";

    List<InvolveModel> getTrainingToRegisterForUsername(final String username);
    long countTrainingToRegisterForUsername(final String username);
    List<WorkGroupActivityModel> getOnlineTraining(List<String> groupes);
    List<WorkGroupActivityModel> getWorkgroupForTraining(final String trainig);
    void registerForTraining(Long involve, String group) throws ModelServiceException;
    void startTraining(Long involve) throws ModelServiceException;
    void markTrainingAsFinish(Long concern, Long  training) throws ModelServiceException;
    void unregisterForTraining(Long involve, String group) throws ModelServiceException;
    long countRegisterTraining(final String username);
    long countDoneTraining(final String username);
    long countOnlineTraining(final String username);
    List<InvolveModel> getRegisterTraining(final String username);
    List<InvolveModel> getDoneTrainings(final String username);
    TrainingModel getTraining(final String code);
    Float getTrainingSkill(final String username, final String code) ;
    InvolveModel registerOnlineTraining(final String groupe, final String username) throws ModelServiceException;
}
