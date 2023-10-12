package cm.nzock.mylearning.services.impl;

import cm.nzock.mylearning.services.LeconActivityService;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.core.services.AbstractDataService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.LeconActivityModel;
import cm.platform.myleaninig.core.TrainingModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DefaultLeconActivityService extends AbstractDataService implements LeconActivityService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLeconActivityService.class);

    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ModelService modelService;

    @Override
    public Map<String, String> create(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        return super.createOrUpdate(context, (ctx, item) ->{
            if (hasParent(ctx)) {
                Integer parent = Integer.valueOf(""+context.get("parent"));
                try {
                    TrainingModel training = (TrainingModel) flexibleSearchService.find(parent.longValue(), TrainingModel._TYPECODE).orElse(null);
                    if (Objects.nonNull(training)) {
                        ((LeconActivityModel)item).setTraining(training);
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
        return super.fetchItems(context, container ->{
            if (hasParent(context)){
                Integer parent = Integer.valueOf(""+context.get("parent"));
                container.addEq("training.pk", parent.longValue());
            }
        });
    }

    private boolean hasParent(Map context) {
        Object parentParam  = context.get("parent");
        return (parentParam instanceof String && StringUtils.isNotBlank((String) parentParam)) || (parentParam instanceof Integer && Objects.nonNull(parentParam));
    }

    @Override
    public long nbreOfFetchItems(Map context) throws ClassNotFoundException {
        return super.nbreOfFetchItems(context, container ->{
            if (hasParent(context)){
                Integer parent = Integer.valueOf(""+context.get("parent"));
                container.addEq("training.pk", parent.longValue());
            }
        });
    }
}
