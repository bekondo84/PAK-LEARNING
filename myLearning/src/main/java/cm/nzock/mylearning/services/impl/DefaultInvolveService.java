package cm.nzock.mylearning.services.impl;

import cm.nzock.mylearning.services.InvolveService;
import cm.platform.basecommerce.core.services.AbstractDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DefaultInvolveService extends AbstractDataService implements InvolveService {

    @Override
    public List fetchItems(Map context) throws ClassNotFoundException {
        return super.fetchItems(context, container -> {
            final Integer parent =  Objects.nonNull(context.get("parent")) && StringUtils.isNotBlank(context.get("parent").toString().trim()) ? (Integer) context.get("parent") : null;
            if (Objects.nonNull(parent)) {
                container.addEq("training.pk", parent.longValue());
            }
        });
    }

    @Override
    public long nbreOfFetchItems(Map context) throws ClassNotFoundException {
        return super.nbreOfFetchItems(context, container -> {
            final Integer parent = Objects.nonNull(context.get("parent")) && StringUtils.isNotBlank(context.get("parent").toString().trim()) ? (Integer) context.get("parent") : null;
            if (Objects.nonNull(parent)) {
                container.addEq("training.pk", parent.longValue());
            }
        });
    }
}
