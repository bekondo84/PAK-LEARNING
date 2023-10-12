package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.ParticipantData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.ParticipantModel;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class ParticipantDataConverter implements Converter<ParticipantModel, ParticipantData> {
    @Override
    public ParticipantData convert(ParticipantModel source) {
        final ParticipantData target = new ParticipantData();
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        if (Objects.nonNull(source.getGender())) {
            target.setGender(source.getGender().name());
        }
        target.setSurname(source.getSurname());
        target.setTown(source.getTown());
        return target;
    }
}
