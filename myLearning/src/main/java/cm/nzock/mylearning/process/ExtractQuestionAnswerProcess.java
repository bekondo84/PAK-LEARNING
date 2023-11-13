package cm.nzock.mylearning.process;

import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.ExcelService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.FeedbackModel;
import cm.platform.myleaninig.core.MultiAnswerQuestionModel;
import cm.platform.myleaninig.core.QuestionAnswerModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component("extractQuestionAnswerProcess")
public class ExtractQuestionAnswerProcess implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ExtractQuestionAnswerProcess.class);

    @Autowired
    private ExcelService excelService ;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        LOG.info("INSIDE ExtractQuestionAnswerProcess : ");
        final MultipartFile file = (MultipartFile) context.get("file");
        final Map<String, MultiAnswerQuestionModel> questionsBank = (Map<String, MultiAnswerQuestionModel>) context.get("bank");
        if (Objects.isNull(file)) {
            context.put("error", "File not found exception") ;
            return "KO" ;
        }
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            context.put("error", "Bad extension exception accept extension is .xlsx") ;
            return "KO" ;
        }
        if (Objects.isNull(questionsBank)) {
            context.put("error", "No Question bank found") ;
            return "KO" ;
        }
        try {
            List<List<String>> data = excelService.excelToJava(file.getInputStream(), 1);
           // LOG.info("INSIDE ExtractQuestionAnswerProcess : "+data);
            List<String> headers = data.get(0);

            for (List<String> row : data.subList(1, data.size())) {
                if (CollectionUtils.isEmpty(row)) {
                    continue;
                }
                MultiAnswerQuestionModel question = getQuestion(questionsBank, headers, row);
                if (question == null) continue;
                QuestionAnswerModel answer = new QuestionAnswerModel();
                final String code = row.get(headers.indexOf("code"));
                final List<QuestionAnswerModel> answers = new ArrayList<>();
                answers.addAll(CollectionUtils.emptyIfNull(question.getQuestion()));
                if (answers.stream().anyMatch(q -> q.getCode().equalsIgnoreCase(code))) {
                    answer = answers.stream().filter(q -> q.getCode().equals(code)).findAny().get();
                } else {
                    answers.add(answer);
                }
                fillQuestionAnwser(headers, row, answer);
                question.setQuestion(answers);
            }
            //Update database
            for (MultiAnswerQuestionModel item: questionsBank.values()) {
                modelService.save(item);
            }

            return "OK";
        } catch (IOException | ModelServiceException e) {
            throw  new BusinessProcessException(e);
        }
    }

    private void fillQuestionAnwser(List<String> headers, List<String> row, QuestionAnswerModel answer) {
        for (String header : headers) {
            if (headers.indexOf(header) >= row.size()) {
                continue;
            }
             String value = row.get(headers.indexOf(header));
            if (header.trim().equalsIgnoreCase("code")) {
               answer.setCode(value);
            } else if (header.trim().equalsIgnoreCase("text[fr]")) {
                answer.setText(Locale.FRENCH, value);
            } else if (header.trim().equalsIgnoreCase("text[en]")) {
                answer.setText(Locale.ENGLISH, value);
            } else if (header.trim().equalsIgnoreCase("mark")) {
                answer.setMark(0.0f);
                if (StringUtils.isNotBlank(value)) {
                    answer.setMark(Float.valueOf(value));
                }
            } else if (header.trim().equalsIgnoreCase("feedback")) {
               if (StringUtils.isNotBlank(value)) {
                   FeedbackModel feedback = (FeedbackModel) flexibleSearchService.find(value, "code", FeedbackModel._TYPECODE).orElse(null);
                   answer.setFeedback(feedback);
               }
            }
        }
    }

    private MultiAnswerQuestionModel getQuestion(Map<String, MultiAnswerQuestionModel> questionsBank, List<String> headers, List<String> row) {
        String questionCode =  row.get(headers.indexOf("question")) ;
        MultiAnswerQuestionModel question = questionsBank.get(questionCode);
        if (Objects.isNull(question)) {
            Optional multi = flexibleSearchService.find(row.get(headers.indexOf("question")), "code", MultiAnswerQuestionModel._TYPECODE);
            if (!multi.isPresent()) {
                return null;
            }
            question = (MultiAnswerQuestionModel) multi.get();
            questionsBank.put(question.getCode(), question);
        }
        return question;
    }
}
