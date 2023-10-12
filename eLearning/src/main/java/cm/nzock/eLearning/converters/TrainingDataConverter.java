package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.AbstractActivityData;
import cm.nzock.eLearning.beans.InvolveData;
import cm.nzock.eLearning.beans.TrainingData;
import cm.nzock.eLearning.populators.AbstractActivityPopulator;
import cm.nzock.eLearning.services.TrainingService;
import cm.nzock.eLearning.utils.Helpers;
import cm.platform.basecommerce.core.enums.InvolveType;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.TrainingModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TrainingDataConverter implements Converter<TrainingModel, TrainingData> {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyy HH:mm");
    @Autowired
    private MediaDataConverter mediaConverter;
    @Autowired
    private ThemeDataConverter themeConverter ;
    @Autowired
    private AbstractActivityPopulator activityPopulator;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private ParticipantDataConverter participantConverter;

    @Override
    public TrainingData convert(TrainingModel source) {
        String username = Helpers.getCurrentUsername();
        TrainingData target = new TrainingData();
        target.setCode(source.getCode());
        target.setName(source.getName(Locale.getDefault()));
        StringBuffer buffer = new StringBuffer("/backoffice/api/v1/public/media/");
        buffer.append(source.getImage().getCode());
        target.setImage(buffer.toString());
        target.setResume(source.getResume(Locale.getDefault()));
        target.setStartAt(SDF.format(source.getBeginAt()));
        target.setEndAt(SDF.format(source.getEndAt()));
        target.setDescription(source.getDescription(Locale.getDefault()));
        if (Objects.nonNull(source.getTheme())) {
            target.setTheme(themeConverter.convert(source.getTheme()));
        }
        CollectionUtils.emptyIfNull(source.getActivities()).forEach(activity -> {
            final AbstractActivityData data = new AbstractActivityData();
            activityPopulator.populate(activity, data);
            target.addActivity(data);
        });
        target.setSkill(trainingService.getTrainingSkill(username, source.getCode()));
        if (Objects.nonNull(source.getState())) {
            target.setState(source.getState().getCode());
        }
        target.setInvolves(getTrainers(source));

        target.setConcern(source.getInvolves().stream()
                .filter(inv -> inv.getConcern().getCode().equals(username))
                .map(inv -> getInvolveData(inv)).findAny().orElse(null));
        return target;
    }

    private List<InvolveData> getTrainers(TrainingModel source) {
        return CollectionUtils.emptyIfNull(source.getInvolves()).stream()
                .filter(i->Objects.nonNull(i.getType()) && i.getType().getCode().equals(InvolveType.TEACHER.getCode()))
                .map(inv -> {
                    return getInvolveData(inv);
                }).collect(Collectors.toList());
    }

    private InvolveData getInvolveData(InvolveModel inv) {
        final InvolveData target = new InvolveData();
        target.setPk(inv.getPK());
        if (Objects.nonNull(inv.getType())) {
            target.setInvolveType(inv.getType().getCode());
        }
        if (Objects.nonNull(inv.getConcern())) {
            target.setConcern(participantConverter.convert(inv.getConcern()));
        }
        target.setRegister(Objects.nonNull(inv.getRegister()) ? inv.getRegister() : false);
        if (Objects.nonNull(inv.getRegisterDate())) {
            target.setRegisterDate(SDF.format(inv.getRegisterDate()));
        }
        target.setMark(inv.getMark());
        if (Objects.nonNull(inv.getResult())) {
            target.setDecision(inv.getResult().getCode());
        }
        target.setPresent(BooleanUtils.toBoolean(inv.getIsPresent()));
        if (Objects.nonNull(inv.getWorkgroup())) {
            target.setOnlineRegister(BooleanUtils.toBoolean(inv.getWorkgroup().getOnlineRegister()));
        }
        return target;
    }

}
