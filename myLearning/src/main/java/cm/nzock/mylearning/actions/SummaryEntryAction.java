package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.SummaryEntryService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.LeconActivityModel;
import cm.platform.myleaninig.core.SummaryEntryModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Objects;

@ActionService("summaryEntryAction")
public class SummaryEntryAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(SummaryEntryAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ObjectMapper mapper ;
    @Autowired
    private SummaryEntryService summaryEntryService;


    @Action(value = "save", scope = HttpMethod.POST)
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {;
        SummaryEntryModel entry = mapper.readValue(context.get("data"), SummaryEntryModel.class);
        LeconActivityModel lecon = getLeconActivity(context);
        if (Objects.nonNull(lecon)) {
            entry.setLecon(lecon);
            modelService.save(entry);
        }
        context.put("data", mapper.writeValueAsString(entry));
        return context;
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", summaryEntryService.fetchItems(context));
        context.put("count", summaryEntryService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "summaryCount", scope = HttpMethod.GET)
    public Map resultCount(Map context) throws ClassNotFoundException {
        context.put("type", SummaryEntryModel.class.getTypeName());
        context.put("count", summaryEntryService.nbreOfFetchItems(context));
        return context;
    }

    private LeconActivityModel getLeconActivity(Map<String, String> context) throws ModelServiceException {
        LeconActivityModel leconActivity;
        //Get parent
        String parent = context.get("parent");
        if (StringUtils.isNotBlank(parent)) {
            leconActivity = (LeconActivityModel) flexibleSearchService.find(Long.valueOf(parent), LeconActivityModel._TYPECODE).orElse(null);
            return leconActivity ;
        }
        return null;
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
