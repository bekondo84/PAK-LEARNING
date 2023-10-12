package cm.nzock.eLearning.services;

import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.MultiAnswerModel;
import cm.platform.myleaninig.core.OpenQuestionAnswerModel;
import cm.platform.myleaninig.core.TrueOrFalseAnswerModel;

public interface AnswerService {

    /**
     *
     * @param pk
     * @param answer
     * @return
     */
    TrueOrFalseAnswerModel evaluateTrueOrFalseQuestion(final Long pk, final String answer) throws ModelServiceException;

    /**
     *
     * @param pk
     * @param answer
     * @return
     * @throws ModelServiceException
     */
    MultiAnswerModel evaluateMultiAnswerQuestion(final Long pk, final String answer) throws ModelServiceException ;

    /**
     * Evaluation des questions ouvertes
     * @param answer
     * @return
     * @throws ModelServiceException
     */
    OpenQuestionAnswerModel evaluateOpenQuestion(final String pk, final String answer) throws ModelServiceException;
}
