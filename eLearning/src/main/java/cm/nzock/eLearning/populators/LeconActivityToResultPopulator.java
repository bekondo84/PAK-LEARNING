package cm.nzock.eLearning.populators;

import cm.platform.basecommerce.populator.Populator;
import cm.platform.myleaninig.core.LeconActivityModel;
import cm.platform.myleaninig.core.LeconResultModel;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class LeconActivityToResultPopulator implements Populator<LeconActivityModel, LeconResultModel> {
    @Override
    public void populate(LeconActivityModel source, LeconResultModel target)  {
         target.setActivity(source);
         if (Objects.isNull(target.getPK())) {
             target.setBeginAt(new Date());
             target.setDateCreation(new Date());
             target.setMark(0.0f);
             target.setPercent(source.getPercent());
         }
    }
}
