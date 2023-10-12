package cm.nzock.eLearning.facades;

import cm.nzock.eLearning.beans.InvolveData;
import cm.nzock.eLearning.beans.TrainingData;
import cm.nzock.eLearning.beans.WorkgroupActivityData;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.WorkGroupActivityModel;

import java.util.List;
import java.util.Set;

public interface TrainingFacade {
    List<InvolveData> getTrainingToRegisterForUsername(final String username);
    long countTrainingToRegisterForUsername(final String username);
    List<WorkgroupActivityData> getWorkgroupForTraining(final String trainig);
    void registerForTraining(Long involve, String group) throws ModelServiceException;
    void startTraining(Long involve) throws ModelServiceException;
     long countRegisterTraining(final String username);
    List<InvolveData> getRegisterTraining(String username);
    List<InvolveData> getDoneTrainings(String username);
    void unregisterForTraining(Long involvePk, String group) throws ModelServiceException;
    TrainingData getTraining(final String code);
    Set<WorkgroupActivityData> getOnlineTraining(final String username);
    InvolveData subscribeOnlineTraining(final String code, final String username) throws ModelServiceException;
}
