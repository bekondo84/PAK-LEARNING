package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.TimesheetData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.TimeSheetModel;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TimesheetConverter implements Converter<TimeSheetModel, TimesheetData> {
    @Override
    public TimesheetData convert(TimeSheetModel source) {
        final TimesheetData target = new TimesheetData();
        target.setDay(source.getDay());
        target.setEndAt(source.getEndAt());
        target.setStartAt(source.getStartAt());
        target.setSubject(source.getSubject(Locale.getDefault()));
        return target;
    }
}
