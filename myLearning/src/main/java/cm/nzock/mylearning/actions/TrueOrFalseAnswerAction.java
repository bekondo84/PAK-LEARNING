package cm.nzock.mylearning.actions;

import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.enums.TrueFalseResultState;
import cm.platform.basecommerce.services.*;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.FeedbackModel;
import cm.platform.myleaninig.core.ThemeModel;
import cm.platform.myleaninig.core.TrueFalseQuestionModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@ActionService("trueOrFalseAnswerAction")
public class TrueOrFalseAnswerAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(TrueOrFalseAnswerAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private CsvService csvService ;
    @Autowired
    private EnumerationService enumService ;

    @Action(value = "upload", scope = HttpMethod.POST)
    public Map upload(Map context) throws IOException, ModelServiceException {
        final MultipartFile file = (MultipartFile) context.get("file");
        final String filename = file.getOriginalFilename();
        List<List<String>> data  = new ArrayList<>();
        if (Objects.nonNull(file)) {
            if (filename.endsWith(".csv")) {
                data  = csvService.csvToJava(file.getInputStream());
            } else if (filename.endsWith(".xlsx")) {
                data = excelService.excelToJava(file.getInputStream());
            }
        }
        LOG.info(String.format("Data : %s", data));
        if (CollectionUtils.isNotEmpty(data)) {
            List<String> headers = data.get(0);
            int index = 0 ;
            for (List<String> record : data.subList(1, data.size())) {
                LOG.info(String.format("Row : %s", record));
                TrueFalseQuestionModel trueFalse = new TrueFalseQuestionModel();
                Optional optional = flexibleSearchService.find(record.get(headers.indexOf("code")), "code", TrueFalseQuestionModel._TYPECODE);
                if (optional.isPresent()) {
                    trueFalse = (TrueFalseQuestionModel) optional.get();
                }
                optional = flexibleSearchService.find(record.get(headers.indexOf("theme")), "code", ThemeModel._TYPECODE);
                if (!optional.isPresent()) {
                    continue;
                }
                if (StringUtils.isBlank(record.get(headers.indexOf("mark")))) {
                    continue;
                }
                String value = record.get(headers.indexOf("answer")) ;
                if (StringUtils.isBlank(value) || !Arrays.asList("TRUE", "FALSE").contains(value.toUpperCase())) {
                    continue;
                }
                fillTrueOrFalseAnswer(headers, record, trueFalse, optional);
                index ++ ;
                modelService.save(trueFalse);
            }
        }
        return context ;
    }

    private void fillTrueOrFalseAnswer(List<String> headers, List<String> record, TrueFalseQuestionModel trueFalse, Optional optional) {
        String value;
        for (String header : headers) {
            int index = headers.indexOf(header);
            if (index < 0 || index >= record.size()) {
                continue;
            }
            value = record.get(index);
            if (header.trim().equalsIgnoreCase("code")) {
                trueFalse.setCode(value);
            } else if (header.trim().equalsIgnoreCase("name[fr]")) {
                 trueFalse.setName(Locale.FRENCH, value);
            } else if (header.trim().equalsIgnoreCase("name[en]")) {
                trueFalse.setName(Locale.ENGLISH, value);
            } else  if (header.trim().equalsIgnoreCase("theme")) {
                trueFalse.setTheme((ThemeModel) optional.get());
            } else if (header.trim().equalsIgnoreCase("text[fr]")) {
                trueFalse.setText(Locale.FRENCH, value);
            }  else if (header.trim().equalsIgnoreCase("text[en]")) {
                trueFalse.setText(Locale.ENGLISH, value);
            }  else if (header.trim().equalsIgnoreCase("ready")) {
                trueFalse.setReady(Boolean.valueOf(value));
            } else if (header.trim().equalsIgnoreCase("mark")) {
                trueFalse.setMark(Float.valueOf(value));
            } else if (header.trim().equalsIgnoreCase("penality")) {
                trueFalse.setPenality(0.0f);
                if (StringUtils.isNotBlank(value)) {
                    trueFalse.setPenality(Float.valueOf(value));
                }
            } else if (header.trim().equalsIgnoreCase("generalFeeback")) {
                Optional feedback = flexibleSearchService.find(record.get(headers.indexOf("generalFeeback")), "generalFeeback", FeedbackModel._TYPECODE);
                if (feedback.isPresent()) {
                    trueFalse.setGeneralFeeback((FeedbackModel) feedback.get());
                }
            } else if (header.trim().equalsIgnoreCase("feedbackifok")) {
                Optional feedback = flexibleSearchService.find(record.get(headers.indexOf("feedbackifok")), "feedbackifok", FeedbackModel._TYPECODE);
                if (feedback.isPresent()) {
                    trueFalse.setFeedbackifko((FeedbackModel) feedback.get());
                }
            } else if (header.trim().equalsIgnoreCase("feedbackifko")) {
                Optional feedback = flexibleSearchService.find(record.get(headers.indexOf("feedbackifko")), "feedbackifko", FeedbackModel._TYPECODE);
                if (feedback.isPresent()) {
                    trueFalse.setFeedbackifko((FeedbackModel) feedback.get());
                }
            } else if (header.trim().equalsIgnoreCase("answer")) {
               trueFalse.setAnswer(enumService.getEnumerationValue(value, TrueFalseResultState.class));
            }
        }
    }

    @Override
    protected ModelService getModelService() {
        return modelService;
    }

    @Override
    protected FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    @Override
    protected MetaTypeService getMetaTypeService() {
        return metaTypeService;
    }
}
