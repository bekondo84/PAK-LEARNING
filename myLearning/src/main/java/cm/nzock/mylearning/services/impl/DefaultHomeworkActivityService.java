package cm.nzock.mylearning.services.impl;

import cm.nzock.mylearning.services.HomeworkActivityService;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.core.services.AbstractDataService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.HomeworkActivityModel;
import cm.platform.myleaninig.core.TrainingModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

@Service
public class DefaultHomeworkActivityService extends AbstractDataService implements HomeworkActivityService {
   private static final Logger LOG  = LoggerFactory.getLogger(DefaultHomeworkActivityService.class);

    @Override
    public Map<String, String> create(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        return super.createOrUpdate(context, (ctx, item) -> {
            if (StringUtils.isNotBlank(ctx.get("parent"))) {
                Integer parent = Integer.valueOf(context.get("parent"));
                try {
                    TrainingModel training = (TrainingModel) flexibleSearchService.find(parent.longValue(), TrainingModel._TYPECODE).orElse(null);
                    if (Objects.nonNull(training)) {
                        ((HomeworkActivityModel)item).setTraining(training);
                    }
                } catch (ModelServiceException e) {
                    LOG.error(e.getMessage());
                    throw new NzockException(e);
                }
            }

        });
    }

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
