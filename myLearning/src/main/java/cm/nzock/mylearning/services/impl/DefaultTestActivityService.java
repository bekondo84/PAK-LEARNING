package cm.nzock.mylearning.services.impl;

import cm.nzock.mylearning.services.TestActivityService;
import cm.platform.basecommerce.core.services.AbstractDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DefaultTestActivityService extends AbstractDataService implements TestActivityService {
    @Override
    public List fetchItems(Map context) throws ClassNotFoundException {
        return super.fetchItems(context, container -> {
            if (hasParent(context)) {
               final Integer parent = Objects.nonNull(context.get("parent")) ? Integer.valueOf(""+context.get("parent")) : null;
                container.addEq("training.pk", parent.longValue());
            }
        });
    }

    @Override
    public long nbreOfFetchItems(Map context) throws ClassNotFoundException {
        return super.nbreOfFetchItems(context, container -> {
            if (hasParent(context)) {
                final Integer parent = Objects.nonNull(context.get("parent")) ? Integer.valueOf(""+context.get("parent")) : null;
                container.addEq("training.pk", parent.longValue());
            }
        });
    }

    private boolean hasParent(Map context) {
        return (context.get("parent") instanceof String && StringUtils.isNotBlank((String) context.get("parent")))
                || (context.get("parent") instanceof Integer && Objects.nonNull(context.get("parent")));
    }
}
