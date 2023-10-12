package cm.nzock.eLearning.services;

import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.LeconEvaluationModel;
import cm.platform.myleaninig.core.LeconResultModel;

public interface LeconResultService {

    LeconResultModel getLeconResultFor(final String activity, final String username);

    LeconResultModel nextStep(final Long pk) throws BusinessProcessException;

    LeconResultModel previousStep(final Long pk) throws BusinessProcessException;

    LeconEvaluationModel getEvaluation(final Long question, final Long result) throws BusinessProcessException;

    LeconResultModel  closeActivity(final Long pk) throws BusinessProcessException;

    void submitEvaluation(final Long pk) throws BusinessProcessException;
}
