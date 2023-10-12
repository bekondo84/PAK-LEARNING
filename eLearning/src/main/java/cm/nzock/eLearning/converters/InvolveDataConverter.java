package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.InvolveData;
import cm.nzock.eLearning.services.ActivityService;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.InvolveModel;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class InvolveDataConverter implements Converter<InvolveModel, InvolveData> {

    @Autowired
   private ParticipantDataConverter participantConverter;
    @Autowired
    private TrainingDataConverter trainingConverter;
    @Autowired
    private ActivityService activityService;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyy HH:mm");

    @Override
    public InvolveData convert(InvolveModel source) {
        final InvolveData target = new InvolveData();
        target.setPk(source.getPK());
        if (Objects.nonNull(source.getType())) {
            target.setInvolveType(source.getType().getCode());
        }
        if (Objects.nonNull(source.getConcern())) {
            target.setConcern(participantConverter.convert(source.getConcern()));
        }
        target.setRegister(Objects.nonNull(source.getRegister()) ? source.getRegister() : false);
        if (Objects.nonNull(source.getRegisterDate())) {
            target.setRegisterDate(SDF.format(source.getRegisterDate()));
        }
        if (Objects.nonNull(source.getWorkgroup()) && Objects.nonNull(source.getWorkgroup().getBeginAt())) {
            target.setRegistrable(isRegistrable(source));
        }
        if (Objects.nonNull(source.getTraining())) {
            target.setTraining(trainingConverter.convert(source.getTraining()));
        } else if (Objects.nonNull(source.getWorkgroup())) {
            target.setTraining(trainingConverter.convert(source.getWorkgroup().getTraining()));
        }
        target.setMark(source.getMark());
        if (Objects.nonNull(source.getResult())) {
            target.setDecision(source.getResult().getCode());
        }
        target.setPresent(BooleanUtils.toBoolean(source.getIsPresent()));
        if (Objects.nonNull(source.getWorkgroup())) {
            target.setOnlineRegister(BooleanUtils.toBoolean(source.getWorkgroup().getOnlineRegister()));
        }
        if (Objects.nonNull(source.getState())) {
            target.setState(source.getState().getCode());
        }
        if (Objects.nonNull(source.getFinishedDate())) {
            target.setFinishedDate(SDF.format(source.getFinishedDate()));
        }
        if (Objects.nonNull(source.getStartingDate())) {
            target.setStartingDate(SDF.format(source.getStartingDate()));
        }
        if (Objects.nonNull(source.getConcern()) && Objects.nonNull(source.getTraining())) {
            target.setNbreOfCompleteActivities((short) activityService.nbreOfActivitiesComplete(source.getConcern().getPK(), source.getTraining().getPK()));
        }
        return target;
    }

    private boolean isRegistrable(InvolveModel source) {
        return !source.getWorkgroup().getBeginAt().after(new Date())
                || !source.getWorkgroup().getOnlineRegister();
    }
}
