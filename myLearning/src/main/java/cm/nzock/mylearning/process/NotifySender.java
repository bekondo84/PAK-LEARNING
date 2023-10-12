package cm.nzock.mylearning.process;

import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.mail.MailModel;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.SettingService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.RequestSendModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@ActionService("notifySender")
public class NotifySender implements Action {

    private static Logger LOG = LoggerFactory.getLogger(NotifySender.class);

    @Autowired
    private SettingService settingService;
    @Autowired
    private ModelService modelService;
    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        final RequestSendModel request = (RequestSendModel) context.get("request");

        final String from =  settingService.getMailReciever();
        assert StringUtils.isNotBlank(from) : "No email recieve was set please set someone to the backoffice configuration";

        final StringBuffer initialRequest = new StringBuffer();
        final MailModel mail = new MailModel();
        mail.setUid(request.getCode());
        mail.setSubject(String.format("PAK-ELEARNING Response message to your request: %s", request.getMessageContent()));
        mail.setBody(request.getResponse());
        mail.setTo(request.getSender());
        mail.setFrom(from);

        try {
            modelService.save((mail));
            return "OK";
        } catch (ModelServiceException e) {
            LOG.error(String.format("Something went wrong", e));
        }
        return "KO";
    }
}
