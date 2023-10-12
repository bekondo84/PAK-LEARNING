package cm.nzock.mylearning.services.impl;

import cm.nzock.mylearning.services.RequestSendService;
import cm.platform.basecommerce.core.enums.RequestSendState;
import cm.platform.basecommerce.core.services.AbstractDataService;
import cm.platform.myleaninig.core.RequestSendModel;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DefaultRequestSendService extends AbstractDataService implements RequestSendService {
    @Override
    public long countRequestToProcess(Map context) throws ClassNotFoundException {
        context.put("type", RequestSendModel.class.getTypeName());
        return super.nbreOfFetchItems(context, container -> {
            container.addEq("state.code", RequestSendState.CREATE.getCode());
        });
    }
}
