package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.ClassroomData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.ClassroomModel;
import org.springframework.stereotype.Component;

@Component
public class ClassroomConverter implements Converter<ClassroomModel, ClassroomData> {

    @Override
    public ClassroomData convert(ClassroomModel source) {
        final ClassroomData target = new ClassroomData();
        target.setCode(source.getCode());
        target.setAdresse(source.getAdresse());
        target.setName(source.getName());
        target.setSize(source.getSize());
        return target;
    }
}
