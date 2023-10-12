package cm.nzock.eLearning.populators;

import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.populator.Populator;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TestActivityToResultPopulator implements Populator<TestActivityModel, TestResultModel> {
    public static final Logger LOG = LoggerFactory.getLogger(TestActivityToResultPopulator.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public void populate(TestActivityModel source, TestResultModel target) throws BusinessProcessException {
        //Check if Result already exists
         if (Objects.nonNull(getTestResultIfExist(target.getConcern(), source))) {
            return;
        }
        //Create result
        target.setActivity(source);
        target.setBeginAt(new Date());
        target.setExtension("e-learning");

        //target.setPercent();
        try {
            modelService.save(target);
            //Load create target
            target = getTestResultIfExist(target.getConcern(), source);
            //List of available question
            final List<AbstractAnswerModel> reponses = new ArrayList<>() ;
            final List questionaires = new ArrayList<>();
            buildQuestionnaires(source, questionaires);
            final Float markOn = source.getMarkOn();
            Float totalMark = 0.0f ;

            while (CollectionUtils.isNotEmpty(questionaires) && (totalMark <= markOn)) {
                int index = getIndexRandomly(questionaires);
                final Object absquestion = questionaires.get(index);
                totalMark += buildTrueOrFalseAnswer(reponses, target,  absquestion);
                totalMark += buildMultiAnswer(reponses, target, absquestion);
                totalMark += buildOpenAnswer(reponses, target, absquestion);
                questionaires.remove(index);
            }
            //target.setResponse(reponses);
            //modelService.save(target);
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }

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

    private TestResultModel getTestResultIfExist(ParticipantModel concern, final AbstractActivityModel activity) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("concern.code", concern.getCode());
        container.addEq("activity.code", activity.getCode());
        List results = flexibleSearchService.doSearch(TestResultModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
        return CollectionUtils.isEmpty(results) ? null : (TestResultModel) results.get(0);
    }
    private Float buildMultiAnswer(List<AbstractAnswerModel> reponses, TestResultModel test, Object absquestion) throws ModelServiceException {
        if (absquestion instanceof MultiAnswerQuestionModel) {
           // LOG.info("INSIDE buildMultiAnswer --------------------");
            MultiAnswerModel answer = new MultiAnswerModel();
            answer.setQuestion((AbstractQuestionModel) absquestion);
            answer.setType("MULTI");
            answer.setResult(test);
            modelService.save(answer);
            return  ((AbstractQuestionModel) absquestion).getMark();
        }
        return 0.0f;
    }

    private Float buildOpenAnswer(List<AbstractAnswerModel> reponses, TestResultModel test, Object absquestion) throws ModelServiceException {
        if (absquestion instanceof OpenQuestionModel) {
            OpenQuestionAnswerModel answer = new OpenQuestionAnswerModel();
            answer.setQuestion((AbstractQuestionModel) absquestion);
            answer.setType("OPEN");
            answer.setResult(test);
            modelService.save(answer);
            return ((AbstractQuestionModel) absquestion).getMark();
        }
        return 0.0f;
    }

    private Float buildTrueOrFalseAnswer(List<AbstractAnswerModel> reponses, TestResultModel test, Object absquestion) throws ModelServiceException {
        if (absquestion instanceof TrueFalseQuestionModel) {
            //LOG.info("INSIDE buildTrueOrFalseAnswer --------------------");
            TrueFalseQuestionModel trueFalseQuestion = (TrueFalseQuestionModel) absquestion;
            final TrueOrFalseAnswerModel answer = new TrueOrFalseAnswerModel();
            answer.setQuestion(trueFalseQuestion);
            answer.setType("TRUEORFALSE");
            answer.setResult(test);
            modelService.save(answer);
            return trueFalseQuestion.getMark();
        }
        return 0.0f;
    }

    private void buildQuestionnaires(TestActivityModel source, List questionaires) {
        questionaires.addAll(CollectionUtils.emptyIfNull(source.getTruefalse()));
        questionaires.addAll(CollectionUtils.emptyIfNull(source.getMultianswers()));
        questionaires.addAll(CollectionUtils.emptyIfNull(source.getOpenQuestions()));
    }

    @Override
    public void reversePopulate(TestResultModel source, TestActivityModel target) {

    }
}
