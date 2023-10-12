package cm.nzock.eLearning.populators;

import cm.nzock.eLearning.beans.ClassroomData;
import cm.nzock.eLearning.beans.WorkgroupActivityData;
import cm.nzock.eLearning.converters.ClassroomConverter;
import cm.nzock.eLearning.converters.MediaDataConverter;
import cm.nzock.eLearning.converters.TimesheetConverter;
import cm.nzock.eLearning.converters.TrainingDataConverter;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.basecommerce.populator.Populator;
import cm.platform.myleaninig.core.WorkGroupActivityModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

@Component
public class WorkgroupActivityPopulator implements Populator<WorkGroupActivityModel, WorkgroupActivityData> {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyyy HH:mm");

    @Autowired
    private AbstractActivityPopulator populator ;
    @Autowired
    private ClassroomConverter classroomConverter;
    @Autowired
    private TimesheetConverter timesheetConverter;
    @Autowired
    private MediaDataConverter mediaConverter ;
    @Autowired
    private TrainingDataConverter trainingDataConverter;

    @Override
    public void populate(WorkGroupActivityModel source, WorkgroupActivityData target) {
        target.setCode(source.getCode());
        if (Objects.nonNull(source.getBeginAt())) {
            target.setBeginAt(SDF.format(source.getBeginAt()));
        }
        if (Objects.nonNull(source.getEndAt())) {
            target.setEndAt(SDF.format(source.getEndAt()));
        }
        if (Objects.nonNull(source.getTraining())) {
            target.setTraining(trainingDataConverter.convert(source.getTraining()));
        }
        target.setDescription(source.getDescription(Locale.getDefault()));
        target.setName(source.getName(Locale.getDefault()));
        target.setVirtual(BooleanUtils.toBoolean(source.getVirtual()));
        if (Objects.nonNull(source.getClassroom())) {
            target.setClassroom(classroomConverter.convert(source.getClassroom()));
        }
        target.setSize(source.getSize());
        target.setAvailable(source.getAvailable());
        target.setReserve(source.getReserve());
        target.setOnlineRegister(source.getOnlineRegister());
        CollectionUtils.emptyIfNull(source.getTimesheets())
                .forEach(ts -> target.addTimesheet(timesheetConverter.convert(ts)));
    }

    @Override
    public void reversePopulate(WorkgroupActivityData source, WorkGroupActivityModel target) {

    }

}
