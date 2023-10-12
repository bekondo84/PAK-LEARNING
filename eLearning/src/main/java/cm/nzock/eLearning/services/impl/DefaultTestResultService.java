package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.services.TestResultService;
import cm.nzock.eLearning.services.TrainingService;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.I18NService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.AbstractAnswerModel;
import cm.platform.myleaninig.core.MultiAnswerModel;
import cm.platform.myleaninig.core.TestResultModel;
import cm.platform.myleaninig.core.TrueOrFalseAnswerModel;
import org.apache.commons.collections4.CollectionUtils;
import org.drools.compiler.shade.org.eclipse.jdt.internal.compiler.util.FloatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DefaultTestResultService implements TestResultService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private I18NService i18NService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private EnumerationService enumService ;
    @Autowired
    private TrainingService trainingService;

    @Override
    public TestResultModel getTestResultFor(String activity, String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("activity.code", activity);
        container.addEq("concern.code", username);
        List resultat = flexibleSearchService.doSearch(TestResultModel.class, container, new HashMap<>(), Collections.singleton("response"), 0, -1);

        return CollectionUtils.isEmpty(resultat) ? null : (TestResultModel) resultat.get(0);
    }

    @Override
    public TestResultModel markAsDone(Long pk) throws ModelServiceException {
        final TestResultModel testResult = (TestResultModel) flexibleSearchService.find(pk, TestResultModel._TYPECODE).orElse(null);
        //Check if all questions are answer and sum total
        Float total = 0.0f;
        if (Objects.nonNull(testResult)) {
            for (AbstractAnswerModel answ : CollectionUtils.emptyIfNull(testResult.getResponse())) {
                if (isTrueOrFalseWithoutAnwser(answ)) {
                    throw new NzockException(i18NService.getLabel("TestResult.allquestionsnoprocess.message","TestResult.allquestionsnoprocess.message"));
                } else if (isMultiWithoutAnswer(answ)) {
                    throw new NzockException(i18NService.getLabel("TestResult.allquestionsnoprocess.message","TestResult.allquestionsnoprocess.message"));
                }
                total+= Objects.nonNull(answ.getMark()) ? answ.getMark() : 0.0f;
                if (Objects.nonNull(answ.getTries()) && answ.getTries()>1) {
                    Float penality = answ.getQuestion().getPenality();
                    total -= Objects.nonNull(penality) ? penality : 0.0f;
                }
                answ.setIsOpen(Boolean.FALSE);
                modelService.save(answ);
            }

        }
        testResult.setMark(total> getMarkOnForActivity(testResult) ? getMarkOnForActivity(testResult) :total);
        testResult.setPercent(testResult.getActivity().getPercent());
        testResult.setEndAt(new Date());
        testResult.setState(enumService.getEnumerationValue(TestResultState.DONE.getCode(), TestResultState.class));
        modelService.save(testResult);
        trainingService.markTrainingAsFinish(testResult.getConcern().getPK(), testResult.getTraining().getPK());
        return testResult;
    }

    private Float getMarkOnForActivity(TestResultModel testResult) {
        return testResult.getActivity().getMarkOn();
    }

    private boolean isMultiWithoutAnswer(AbstractAnswerModel answ) {
        return answ instanceof MultiAnswerModel && Objects.isNull(((MultiAnswerModel) answ).getAnswer());
    }

    private boolean isTrueOrFalseWithoutAnwser(AbstractAnswerModel answ) {
        return answ instanceof TrueOrFalseAnswerModel && Objects.isNull(((TrueOrFalseAnswerModel) answ).getAnswer());
    }
}
