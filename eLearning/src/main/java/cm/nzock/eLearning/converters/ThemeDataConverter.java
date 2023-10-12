package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.ThemeData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.ThemeModel;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ThemeDataConverter implements Converter<ThemeModel, ThemeData> {
    @Override
    public ThemeData convert(ThemeModel source) {
        final ThemeData target = new ThemeData();
        target.setCode(source.getCode());
        target.setName(source.getName(Locale.getDefault()));
        target.setResume(source.getResume(Locale.getDefault()));
        return target;
    }
}
