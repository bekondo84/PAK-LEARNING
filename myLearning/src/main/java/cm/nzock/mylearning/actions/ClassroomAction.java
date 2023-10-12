package cm.nzock.mylearning.actions;

import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.ClassroomModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ActionService("classroomAction")
public class ClassroomAction extends AbstractAction {
    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;

    @Action(value = "nbreOfPlaces", scope = HttpMethod.GET)
    public Map getClassroomSize(Map<String, String> context) throws ModelServiceException {
        final Map result = new HashMap<>(context);

        final long pk = Long.valueOf(String.valueOf(context.get("pk")));
        final ClassroomModel classroom = (ClassroomModel) flexibleSearchService.find(pk, ClassroomModel._TYPECODE).orElse(null);
        if (Objects.nonNull(classroom)) {
            result.put("data",classroom.getSize());
        }
        return result;
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
