package cm.nzock.mylearning.process;

import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.ExcelService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.FeedbackModel;
import cm.platform.myleaninig.core.MultiAnswerQuestionModel;
import cm.platform.myleaninig.core.ThemeModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component("extractQuestionProcess")
public class ExtractQuestionProcess implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ExtractQuestionProcess.class);

    @Autowired
    private ExcelService excelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        final MultipartFile file = (MultipartFile) context.get("file");
        if (Objects.isNull(file)) {
            context.put("error", "File not found exception") ;
            return "KO" ;
        }
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            context.put("error", "Bad extension exception accept extension is .xlsx") ;
            return "KO" ;
        }
        try {
            List<List<String>> data = excelService.excelToJava(file.getInputStream());
            final List<String> headers = data.get(0);
            final Map<String, MultiAnswerQuestionModel> questionsBank = new HashMap<>();

            for (List<String> row : data.subList(1, data.size())) {

                if (isValidRow(headers, row)) {
                    LOG.info(String.format("---------- ROW : %s -------------------- Headers : %s", row, headers));
                    Optional theme = flexibleSearchService.find(row.get(headers.indexOf("theme")), "code", ThemeModel._TYPECODE);
                    if (!theme.isPresent()) {
                        continue;
                    }
                    MultiAnswerQuestionModel question = new MultiAnswerQuestionModel();
                    Optional optional = flexibleSearchService.find(row.get(headers.indexOf("code")), "code", MultiAnswerQuestionModel._TYPECODE);
                    if (optional.isPresent()) {
                        question = (MultiAnswerQuestionModel) optional.get();
                    }
                    fillQuestion(headers, row, theme, question);
                    questionsBank.put(question.getCode(), question);
                }
            }
            context.put("bank", questionsBank);
            return "OK";
        } catch (IOException  e) {
            throw  new BusinessProcessException(e);
        }
    }

    private boolean isValidRow(List<String> headers, List<String> row) {
        return CollectionUtils.isNotEmpty(row) && CollectionUtils.isNotEmpty(headers) && (row.size() == headers.size());
    }

    private void fillQuestion(List<String> headers, List<String> row, Optional theme, MultiAnswerQuestionModel question) {
        for (String header : headers) {
            if (headers.indexOf(header) >= row.size()) {
                continue;
            }
            final String value = row.get(headers.indexOf(header));
            if (header.trim().equalsIgnoreCase("code")) {
                 question.setCode(value);
            } else if (header.trim().equalsIgnoreCase("theme")) {
                question.setTheme((ThemeModel) theme.get());
            } else if (header.trim().equalsIgnoreCase("ready")) {
                question.setReady(Boolean.valueOf(value));
            } else if (header.trim().equalsIgnoreCase("name[fr]")) {
                question.setName(Locale.FRENCH, value);
            } else if (header.trim().equalsIgnoreCase("name[en]")) {
                question.setName(Locale.ENGLISH, value);
            } else if (header.trim().equalsIgnoreCase("text[fr]")) {
                question.setText(Locale.FRENCH, value);
            } else if (header.trim().equalsIgnoreCase("text[en]")) {
                question.setText(Locale.ENGLISH, value);
            } else if (header.trim().equalsIgnoreCase("mark")) {
                question.setMark(0.0F);
                if (StringUtils.isNotBlank(value)) {
                    question.setMark(Float.valueOf(value));
                }
            } else if (header.trim().equalsIgnoreCase("penality")) {
                question.setPenality(0.0f);
                if (StringUtils.isNotBlank(value)) {
                    question.setPenality(Float.valueOf(value));
                }
            } else if (header.trim().equalsIgnoreCase("generalFeeback")) {
                Optional feedback = flexibleSearchService.find(row.get(headers.indexOf("generalFeeback")), "generalFeeback", FeedbackModel._TYPECODE);
                if (feedback.isPresent()) {
                    question.setGeneralFeeback((FeedbackModel) feedback.get());
                }
            }
        }
    }
}
