package cm.nzock.mylearning.actions;

import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.core.services.ProcessDefinitionService;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@ActionService("multiAnswerQuestionAction")
public class MultiAnswerQuestionAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(MultiAnswerQuestionAction.class);

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
    private ProcessDefinitionService processService ;

    @Action(value = "upload", scope = HttpMethod.POST)
    public Map upload(Map context) throws IOException, BusinessProcessException {
        final MultipartFile file = (MultipartFile) context.get("file");
         Map<String, Object> ctx = new HashMap<>();
         ctx.put("file", file);
         LOG.info("INSIDE MultiAnswerQuestionAction");
         processService.process("multiAnswerQuestion", ctx);
        return context ;
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
