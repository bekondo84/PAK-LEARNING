package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.services.AnswerService;
import cm.platform.basecommerce.core.enums.EvaluationMethod;
import cm.platform.basecommerce.core.enums.TrueFalseResultState;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.*;
import org.drools.compiler.shade.org.eclipse.jdt.internal.compiler.util.FloatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultAnswerService implements AnswerService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService ;
    @Autowired
    private EnumerationService enumService;

    @Override
    public TrueOrFalseAnswerModel evaluateTrueOrFalseQuestion(Long pk, String answer) throws ModelServiceException {
        final TrueOrFalseAnswerModel questionAnswer = (TrueOrFalseAnswerModel) flexibleSearchService.find(pk, TrueOrFalseAnswerModel._TYPECODE)
                .orElse(null);
        final TrueFalseQuestionModel question = (TrueFalseQuestionModel) questionAnswer.getQuestion();
        final TestActivityModel testActivity = Objects.nonNull(questionAnswer.getResult()) ? questionAnswer.getResult().getActivity() : null ;
        TrueFalseResultState state  = getTrueFalseResultState(answer);
        questionAnswer.setAnswer(state);
        final TrueFalseResultState correctAnsew = question.getAnswer();
        questionAnswer.setMark(0.0f);
        if (correctAnsew.getCode().equalsIgnoreCase(answer)) {
            questionAnswer.setMark(question.getMark());
        }
        questionAnswer.setTries((short) (questionAnswer.getTries() + 1));
        modelService.save(questionAnswer);
        return questionAnswer;
    }

    private float getMark(TrueFalseQuestionModel question, TrueFalseResultState state) {
        return question.getAnswer().equals(state) ? question.getMark() : 0.0f;
    }

    private TrueFalseResultState getTrueFalseResultState(String answer) {
        TrueFalseResultState state;
        if (answer.equalsIgnoreCase("True")) {
            state = enumService.getEnumerationValue(TrueFalseResultState.TRUE.getCode(), TrueFalseResultState.class);
        } else {
            state = enumService.getEnumerationValue(TrueFalseResultState.FALSE.getCode(), TrueFalseResultState.class);
        }
        return state;
    }

    @Override
    public MultiAnswerModel evaluateMultiAnswerQuestion(Long pk, String answer) throws ModelServiceException {
        final MultiAnswerModel questionAnswer = (MultiAnswerModel) flexibleSearchService.find(pk, MultiAnswerModel._TYPECODE).orElse(null);
        final QuestionAnswerModel givenAnswer = (QuestionAnswerModel) flexibleSearchService.find(Long.valueOf(answer), QuestionAnswerModel._TYPECODE).orElse(null);
        final MultiAnswerQuestionModel question  = (MultiAnswerQuestionModel) questionAnswer.getQuestion();
        final TestActivityModel testActivity = Objects.nonNull(questionAnswer.getResult()) ? questionAnswer.getResult().getActivity() : null;
        if (Objects.nonNull(givenAnswer)) {
            questionAnswer.setAnswer(givenAnswer);
            questionAnswer.setMark(givenAnswer.getMark()*(question.getMark()/100));
            questionAnswer.setTries((short) (questionAnswer.getTries() + 1));
        }
        modelService.save(questionAnswer);
        return questionAnswer;
    }

    @Override
    public OpenQuestionAnswerModel evaluateOpenQuestion(String pk, String answer) throws ModelServiceException {
        final OpenQuestionAnswerModel answerModel = (OpenQuestionAnswerModel) flexibleSearchService.find(Long.valueOf(pk), OpenQuestionAnswerModel._TYPECODE).orElse(null);
        answerModel.setAnswer(answer);
        modelService.save(answerModel);
          return answerModel;
    }
}
