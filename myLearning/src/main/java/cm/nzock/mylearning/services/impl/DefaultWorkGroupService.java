package cm.nzock.mylearning.services.impl;

import cm.nzock.mylearning.services.WorkGroupeService;
import cm.platform.basecommerce.core.services.AbstractDataService;
import cm.platform.basecommerce.core.services.impl.DefaultDataService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.tools.persistence.DAOUtilis;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultWorkGroupService extends AbstractDataService implements WorkGroupeService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List fetchItems(Map context) throws ClassNotFoundException {
        final Map<String, DAOUtilis.OrderType> sortRules= new HashMap<>();
        final Integer offset = (Integer) context.get("offset");
        final Integer max = (Integer) context.get("max");
        final Integer parent = Objects.nonNull(context.get("parent")) && StringUtils.isNotBlank(context.get("parent").toString()) ? (Integer) context.get("parent") : null;
        final String className = (String) context.get("type");
        Class clazz = Class.forName(className);
        RestrictionsContainer container = buildRestrictionContainer(context, sortRules, clazz);
        if (Objects.nonNull(parent)) {
            container.addEq("training.pk", parent.longValue());
        }
        return flexibleSearchService.doSearch(clazz, container, sortRules, new HashSet<>(), offset, max);
    }

    @Override
    public long nbreOfFetchItems(Map context) throws ClassNotFoundException {
        final Map<String, DAOUtilis.OrderType> sortRules= new HashMap<>();
        final Integer offset = (Integer) context.get("offset");
        final Integer max = (Integer) context.get("max");
        final Integer parent = Objects.nonNull(context.get("parent"))  && StringUtils.isNotBlank(context.get("parent").toString())  ? (Integer) context.get("parent") : null;
        final String className = (String) context.get("type");
        Class clazz = Class.forName(className);
        RestrictionsContainer container = buildRestrictionContainer(context, sortRules, clazz);
        if (Objects.nonNull(parent)) {
            container.addEq("training.pk", parent.longValue());
        }
        return flexibleSearchService.count(clazz, container);
    }
}
