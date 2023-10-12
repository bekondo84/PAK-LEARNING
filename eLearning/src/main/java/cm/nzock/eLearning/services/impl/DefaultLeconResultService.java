package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.services.LeconResultService;
import cm.nzock.eLearning.services.TrainingService;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.I18NService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.*;
import org.apache.commons.collections4.CollectionUtils;
import org.drools.compiler.shade.org.eclipse.jdt.internal.compiler.util.FloatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultLeconResultService implements LeconResultService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLeconResultService.class);
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService ;
    @Autowired
    private I18NService i18NService;
    @Autowired
    private EnumerationService enumService ;
    @Autowired
    private TrainingService trainingService;

    @Override
    public LeconResultModel getLeconResultFor(String activity, String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("activity.code", activity);
        container.addEq("concern.code", username);
        List result = flexibleSearchService.doSearch(LeconResultModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
        LOG.info(String.format("Nbre of items fetchs: %s", result.size()));
        return CollectionUtils.isNotEmpty(result) ? (LeconResultModel) result.get(0) : null;
    }

    @Override
    public LeconResultModel nextStep(Long pk) throws BusinessProcessException {
        try {
            LeconResultModel result = getLeconResult(pk);

            if (!result.getState().equals(TestResultState.DONE)) {
                //Proces current step if LeconQuestion
                sumQuestionsMark(pk, result);
            }
            int nextStep = result.getCurrentIndex().getSequence() + 1;
            result = setSummaryForStep(pk, result, nextStep);
            return result ;
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }
    }

    @Override
    public void submitEvaluation(Long pk) throws BusinessProcessException {
        try {
            LeconResultModel result = getLeconResult(pk);
            if (!result.getState().equals(TestResultState.DONE)) {
                //Proces current step if LeconQuestion
                sumQuestionsMark(pk, result);
            }
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }
    }

    @Override
    public LeconResultModel closeActivity(Long pk) throws  BusinessProcessException {
        try {
            LeconResultModel result = getLeconResult(pk);
            //Proces current step if LeconQuestion
            sumQuestionsMark(pk, result);
            //Compute the avarage of the activity
            result = getLeconResult(pk);
            final LeconActivityModel lecon = result.getActivity();
            result.setMark(CollectionUtils.emptyIfNull(result.getEvaluations())
                    .stream().map(eval -> eval.getMark())
                    .reduce(0.0f, (a, b) -> a + b));
            result.setEndAt(new Date());
            result.setState(enumService.getEnumerationValue(TestResultState.DONE.getCode(), TestResultState.class));
            modelService.save(result);
            //Close training
            trainingService.markTrainingAsFinish(result.getConcern().getPK(), result.getTraining().getPK());
            return result;
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }
    }

    private void sumQuestionsMark(Long pk, LeconResultModel result) throws BusinessProcessException, ModelServiceException {
        if (result.getCurrentIndex().getIndex().getNature().equalsIgnoreCase("TEST")) {
            final LeconEvaluationModel evaluation = getEvaluation(result.getCurrentIndex().getIndex().getPK(), pk);
            //Chech if there is answer without
            List<AbstractAnswerModel> evaluations = evaluation.getResponse()
                    .stream().filter(ans -> {
                        if (ans instanceof TrueOrFalseAnswerModel) {
                            return Objects.isNull(((TrueOrFalseAnswerModel) ans).getAnswer());
                        } else  if (ans instanceof MultiAnswerModel) {
                            return  Objects.isNull(((MultiAnswerModel)ans).getAnswer());
                        }
                        return  false;
                    }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(evaluations)) {
                throw new BusinessProcessException(i18NService.getLabel("LeconEvaluation.allquestions.noanwser.exception", "LeconEvaluation.allquestions.noanwser.exception"));
            }
            evaluation.setMark(CollectionUtils.emptyIfNull(evaluation.getResponse())
                    .stream().map(ans -> ans.getMark())
                    .reduce(0.0f, (a, b)->a + b));
            evaluation.setEndAt(new Date());
            evaluation.setState(enumService.getEnumerationValue(TestResultState.DONE.getCode(), TestResultState.class));
            modelService.save(evaluation);
            //Close open question
            for (AbstractAnswerModel answer : CollectionUtils.emptyIfNull(evaluation.getResponse())) {
                answer.setIsOpen(Boolean.FALSE);
                modelService.save(answer);
            }
        }
    }

    @Override
    public LeconResultModel previousStep(Long pk) throws BusinessProcessException {
        try {
            LeconResultModel result = getLeconResult(pk);
            int nextStep = result.getCurrentIndex().getSequence() - 1;
            result = setSummaryForStep(pk, result, nextStep);
            return result ;
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }
    }

    @Override
    public LeconEvaluationModel getEvaluation(Long question, Long result) throws BusinessProcessException {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("question.pk", question);
        container.addEq("result.pk", result);
        List evaluations = flexibleSearchService.doSearch(LeconEvaluationModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
        return CollectionUtils.isNotEmpty(evaluations) ? (LeconEvaluationModel) evaluations.get(0) : null;
    }

    private LeconResultModel setSummaryForStep(Long pk, LeconResultModel result, int nextStep) throws ModelServiceException {
        List<SummaryEntryModel> entries = result.getActivity().getSummary();
        SummaryEntryModel nextEntry = CollectionUtils.emptyIfNull(entries)
                .stream()
                .filter(entry -> entry.getSequence()== nextStep)
                .findAny().orElse(null);
        if (Objects.nonNull(nextEntry)) {
            //Process if Evaluation
            if (nextEntry.getIndex().getNature().equals("TEST")) {
                final LeconQuestionModel leconQuestion = (LeconQuestionModel) flexibleSearchService.find(nextEntry.getIndex().getPK(), LeconQuestionModel._TYPECODE).orElse(null);
                LeconEvaluationModel evaluation = getLeconEvaluation(result, leconQuestion);
                if (Objects.isNull(evaluation)) {
                    createLeconEvaluation(result, leconQuestion);
                }

            }
            result.setCurrentIndex(nextEntry);
            modelService.save(result);
            result = (LeconResultModel) flexibleSearchService.find(pk, LeconResultModel._TYPECODE).orElse(null);
        }
        return result;
    }

    private void createLeconEvaluation(LeconResultModel result, LeconQuestionModel leconQuestion) throws ModelServiceException {
        LeconEvaluationModel evaluation = new LeconEvaluationModel();
        evaluation.setResult(result);
        evaluation.setQuestion(leconQuestion);
        evaluation.setConcern(result.getConcern());
        evaluation.setBeginAt(new Date());
        evaluation.setMark(0.0f);
        evaluation.setTraining(result.getTraining());
        evaluation.setExtension("e-learning");
        //Save evaluation object
        modelService.save(evaluation);
        evaluation = getLeconEvaluation(result, leconQuestion);
        //Build Questions list
        List<AbstractQuestionModel> questionaires = new ArrayList<>(CollectionUtils.emptyIfNull(leconQuestion.getTruefalse()));
        questionaires.addAll(CollectionUtils.emptyIfNull(leconQuestion.getMultianswers()));
        Float markOn = result.getActivity().getMarkOn();
        Float totalMark = 0.0f ;
        final List<AbstractAnswerModel> reponses = new ArrayList<>() ;
        while (CollectionUtils.isNotEmpty(questionaires) || (totalMark >= markOn)) {
            int index = getIndexRandomly(questionaires);
            final Object absquestion = questionaires.get(index);
            totalMark += buildTrueOrFalseAnswer(reponses, evaluation,  absquestion);
            totalMark += buildMultiAnswer(reponses, evaluation, absquestion);
            //totalMark += buildOpenAnswer(reponses, target, absquestion);
            questionaires.remove(index);
        }
        evaluation.setState(enumService.getEnumerationValue(TestResultState.CREATE.getCode(), TestResultState.class));
        modelService.save(evaluation);
    }

    private LeconEvaluationModel getLeconEvaluation(LeconResultModel result, LeconQuestionModel leconQuestion) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("result.pk", result.getPK());
        container.addEq("question.pk", leconQuestion.getPK());
        List evaluations = flexibleSearchService.doSearch(LeconEvaluationModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
        if (CollectionUtils.isNotEmpty(evaluations)) {
            return  (LeconEvaluationModel) evaluations.get(0);
        }
        return null;
    }

    private Float buildMultiAnswer(List<AbstractAnswerModel> reponses, LeconEvaluationModel test, Object absquestion) throws ModelServiceException {
        if (absquestion instanceof MultiAnswerQuestionModel) {
            // LOG.info("INSIDE buildMultiAnswer --------------------");
            MultiAnswerModel answer = new MultiAnswerModel();
            answer.setQuestion((AbstractQuestionModel) absquestion);
            answer.setType("MULTI");
            answer.setLeconResult(test);
            modelService.save(answer);
            return  ((AbstractQuestionModel) absquestion).getMark();
        }
        return 0.0f;
    }

    private Float buildOpenAnswer(List<AbstractAnswerModel> reponses, LeconEvaluationModel test, Object absquestion) throws ModelServiceException {
        if (absquestion instanceof OpenQuestionModel) {
            OpenQuestionAnswerModel answer = new OpenQuestionAnswerModel();
            answer.setQuestion((AbstractQuestionModel) absquestion);
            answer.setType("OPEN");
            answer.setLeconResult(test);
            modelService.save(answer);
            return ((AbstractQuestionModel) absquestion).getMark();
        }
        return 0.0f;
    }

    private Float buildTrueOrFalseAnswer(List<AbstractAnswerModel> reponses, LeconEvaluationModel test, Object absquestion) throws ModelServiceException {
        if (absquestion instanceof TrueFalseQuestionModel) {
            //LOG.info("INSIDE buildTrueOrFalseAnswer --------------------");
            TrueFalseQuestionModel trueFalseQuestion = (TrueFalseQuestionModel) absquestion;
            final TrueOrFalseAnswerModel answer = new TrueOrFalseAnswerModel();
            answer.setQuestion(trueFalseQuestion);
            answer.setType("TRUEORFALSE");
            answer.setLeconResult(test);
            modelService.save(answer);
            return trueFalseQuestion.getMark();
        }
        return 0.0f;
    }
    private int getIndexRandomly(List questionaires) {
        int index = 0 ;
        if (questionaires.size() > 1) {
            index = new Random()
                    .ints(0, questionaires.size() - 1)
                    .findAny().getAsInt();
        }
        return index;
    }

    private LeconResultModel getLeconResult(Long pk) throws ModelServiceException, BusinessProcessException {
        final LeconResultModel result = (LeconResultModel) flexibleSearchService.find(pk, LeconResultModel._TYPECODE).orElse(null);
        if (Objects.isNull(result)) {
            throw new BusinessProcessException(String.format("No LeconResult found with PK %s", pk));
        }
        return result;
    }


}
