package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.LoginComponentData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.basecommerce.services.I18NService;
import cm.platform.myleaninig.cms.LoginComponentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LoginComponentConverter implements Converter<LoginComponentModel, LoginComponentData> {

    @Autowired
    private I18NService i18NService;

    @Override
    public LoginComponentData convert(LoginComponentModel source) {
        final LoginComponentData target = new LoginComponentData();
        target.setLoginLabel(i18NService.getLabel("login.label", "login.label"))
                .setLoginMsge(i18NService.getLabel("login.message", "login.message"))
                .setConnexionBtnLabel(i18NService.getLabel("connexion.label", "connexion.label"))
                .setErrorMsge(i18NService.getLabel("login.error.msge", "login.error.msge"))
                .setMessage(source.getMessage(Locale.getDefault()))
                .setTitle(i18NService.getLabel("application.title", "application.title"))
                .setWelcomeMsge(i18NService.getLabel("welcome.message", "welcome.message"))
                .setPasswordLabel(i18NService.getLabel("password.label", "password.label"));
        return target;
    }
}
