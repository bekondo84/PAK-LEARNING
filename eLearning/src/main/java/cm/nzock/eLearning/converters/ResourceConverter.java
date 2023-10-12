package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.ResourceData;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.ResourceModel;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResourceConverter implements Converter<ResourceModel, ResourceData> {
    @Override
    public ResourceData convert(ResourceModel source) {
        ResourceData target = new ResourceData();
        target.setDescription(source.getDescription(Locale.getDefault()));
        target.setName(source.getName());
        target.setFilename(source.getFile());
        target.setPk(source.getPK());
        return target;
    }
}
