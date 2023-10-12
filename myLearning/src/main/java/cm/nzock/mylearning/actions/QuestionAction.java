package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.QuestionConvert;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.Map;

@ActionService("questionAction")
public class QuestionAction extends AbstractAction {
    private static Logger LOG = LoggerFactory.getLogger(QuestionAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService ;
    @Autowired
    private QuestionConvert convert ;


    @Action(value = "fetch", scope = HttpMethod.GET)
    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        /**final Map result = super.getItems(context);
        List data = (List) result.get("data");
        final List<AbstractQuestionModel> questions = new ArrayList<>();
        CollectionUtils.emptyIfNull(data).stream()
                .forEach(d -> {
                    questions.add(convert.convert(d));
                });
        context.put("data", questions);
        LOG.info(String.format("------------------------------------------ : %s", context));*/
        return super.getItems(context);
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
