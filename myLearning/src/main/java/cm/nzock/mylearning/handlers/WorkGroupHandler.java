package cm.nzock.mylearning.handlers;

import cm.platform.basecommerce.core.handlers.AttributHander;
import cm.platform.myleaninig.core.WorkGroupActivityModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("workGroupHandler")
public class WorkGroupHandler implements AttributHander<WorkGroupActivityModel> {
    @Override
    public void handler(WorkGroupActivityModel item, String property) {
         if (Objects.nonNull(item.getClassroom())) {
             item.setSize(item.getClassroom().getSize());
         }
         item.setReserve(Short.parseShort("0"));
         if (CollectionUtils.isNotEmpty(item.getMembers())) {
             item.setReserve(Short.parseShort(String.valueOf(item.getMembers().size())));
         }
    }
}
