package cm.nzock.eLearning.populators;

import cm.nzock.eLearning.beans.RequestSendData;
import cm.platform.basecommerce.core.enums.RequestSendState;
import cm.platform.basecommerce.populator.Populator;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.myleaninig.core.RequestSendModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RequestSendPopulator implements Populator<RequestSendModel, RequestSendData> {

    @Autowired
    private EnumerationService enumerationService;

    @Override
    public void populate(RequestSendModel source, RequestSendData target) throws Exception {
        target.setSender(source.getSender());
        target.setMessage(source.getMessageContent());
    }

    @Override
    public void reversePopulate(RequestSendData source, RequestSendModel target) {
        final StringBuffer buffer = new StringBuffer(StringUtils.substringBefore(source.getSender(), "@"));
        target.setMessageDate(new Date());
        buffer.append(target.getMessageDate().getTime());
        target.setCode(buffer.toString());
        target.setSender(source.getSender());
        target.setMessageContent(source.getMessage());
        target.setState(enumerationService.getEnumerationValue(RequestSendState.CREATE.getCode(), RequestSendState.class));
    }
}
