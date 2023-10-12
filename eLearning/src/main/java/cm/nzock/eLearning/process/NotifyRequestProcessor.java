package cm.nzock.eLearning.process;

import cm.platform.basecommerce.core.mail.MailModel;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.SettingService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.RequestSendModel;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("notifyRequestProcessor")
public class NotifyRequestProcessor implements Action {

    private static Logger LOG = LoggerFactory.getLogger(NotifyRequestProcessor.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private Environment env ;
    @Autowired
    private SettingService settingService;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        final RequestSendModel request = (RequestSendModel) context.get("request");
        final String to = settingService.getMailReciever();
        assert StringUtils.isNotBlank(to) : "No email recieve was set please set someone to the backoffice configuration";
        final Boolean activate = BooleanUtils.toBoolean(env.getProperty("elearning.notification.activation"));
        //LOG.info(String.format("Notification Action ::::::: processor: %s :::::::: activate: %s", processor, activate));

        if (BooleanUtils.isFalse(activate)) {
            return  "OK";
        }

        MailModel mail = new MailModel();
        mail.setUid(request.getCode());
        mail.setBody(request.getMessageContent());
        mail.setTo(to);
        mail.setFrom(request.getSender());
        mail.setSubject(String.format("PAK-ELEARNING Request code: %s", request.getCode()));
        try {
            modelService.save(mail);
            return "OK";
        } catch (ModelServiceException e) {
           LOG.error(String.format("Something went wrong", e));
        }
        return "KO";
    }
}
