package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.UserData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class UserDataConverter implements Converter<ParticipantModel, UserData> {
    @Override
    public UserData convert(ParticipantModel source) {
        final UserData target = new UserData();
        target.setEmail(source.getEmail());
        target.setGender(Objects.nonNull(source.getGender()) ? source.getGender().name():null);
        target.setLanguage(Objects.nonNull(source.getDefaultLanguage()) ? source.getDefaultLanguage().getCode():"fr");
        target.setUsername(source.getCode());
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setAvatar(Objects.nonNull(source.getAvatar()) ? source.getAvatar().getDownloadUrl():null);
        target.setFullname(Arrays.asList(source.getSurname(), source.getName()).stream().filter(StringUtils::isNoneBlank).reduce("", (s, v) -> s+" "+v));
        return target;
    }
}
