package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.UserData;
import cm.nzock.eLearning.utils.Helpers;
import cm.platform.basecommerce.core.i18n.LocaleModel;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.I18NService;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserDataToParticipantConverter implements Converter<UserData, ParticipantModel> {

    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private I18NService i18NService;

    @Override
    public ParticipantModel convert(UserData source) {
        assert Objects.nonNull(source) : "Convert user data can't be null";

        if (StringUtils.isBlank(source.getUsername())) {
            return null;
        }
        final ParticipantModel participant= (ParticipantModel) flexibleSearchService.find(source.getUsername(), "code", ParticipantModel._TYPECODE).orElse(null);

        if (Objects.isNull(participant)) {
            return null ;
        }
        participant.setName(source.getName());
        participant.setSurname(source.getSurname());
        participant.setEmail(source.getEmail());
        if (StringUtils.isNotBlank(source.getLanguage())) {
            LocaleModel locale = (LocaleModel) flexibleSearchService.find(source.getLanguage(), "code", LocaleModel._TYPECODE).orElse(null);
            participant.setDefaultLanguage(locale);
        }

        return participant;
    }
}
