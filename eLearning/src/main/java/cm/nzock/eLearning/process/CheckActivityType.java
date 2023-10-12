package cm.nzock.eLearning.process;

import cm.platform.basecommerce.core.process.Action;
import cm.platform.myleaninig.core.AbstractActivityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component("checkActivityType")
public class CheckActivityType implements Action {
   public static final Logger LOG = LoggerFactory.getLogger(CheckActivityType.class);

    @Override
    public String execute(Map<String, Object> context) {
        LOG.info("Inside CheckActivityType Bean");
        AbstractActivityModel activity = (AbstractActivityModel) context.get("activity");
        if (Objects.isNull(activity)) {
            return null ;
        }
        LOG.info("Inside CheckActivityType Bean ---- : "+activity.getType());
        return activity.getType();
    }
}
