package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.LeconContentService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.enums.CourseFormat;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.LeconActivityModel;
import cm.platform.myleaninig.core.LeconContentModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Objects;

@ActionService("leconContentAction")
public class LeconContentAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(LeconContentAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ObjectMapper mapper ;
    @Autowired
    private EnumerationService enumService ;
    @Autowired
    private LeconContentService leconContentService;

    @Override
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        //LOG.info("INSIDE LeconContentAction .......................");
        LeconContentModel lecon = mapper.readValue(context.get("data"), LeconContentModel.class);
        LeconActivityModel leconActivity = getLeconActivity(context);
        if (Objects.nonNull(leconActivity)) {
            lecon.setActivity(leconActivity);
            if (Objects.nonNull(lecon.getFormat())) {
                lecon.setFormat(enumService.getEnumerationValue(lecon.getFormat().getCode(),CourseFormat.class));
            }
            modelService.save(lecon);
        }
        context.put("data", mapper.writeValueAsString(lecon));
        return context;
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", leconContentService.fetchItems(context));
        context.put("count", leconContentService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "courseCount", scope = HttpMethod.GET)
    public Map courseCount(Map context) throws ClassNotFoundException {
        context.put("type", LeconContentModel.class.getTypeName());
        context.put("count", leconContentService.nbreOfFetchItems(context));
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
