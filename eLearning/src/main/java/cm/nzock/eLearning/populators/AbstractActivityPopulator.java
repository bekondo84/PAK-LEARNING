package cm.nzock.eLearning.populators;

import cm.nzock.eLearning.beans.AbstractActivityData;
import cm.nzock.eLearning.converters.MediaDataConverter;
import cm.nzock.eLearning.converters.ResourceConverter;
import cm.platform.basecommerce.populator.Populator;
import cm.platform.myleaninig.core.AbstractActivityModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

@Component
public class AbstractActivityPopulator implements Populator<AbstractActivityModel, AbstractActivityData> {

    @Autowired
    private MediaDataConverter mediaConverter ;
    @Autowired
    private ResourceConverter resourceConverter;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyyy HH:mm");

    @Override
    public void populate(AbstractActivityModel source, AbstractActivityData target) {
        target.setCode(source.getCode());
        if (Objects.nonNull(source.getBeginAt())) {
            target.setBeginAt(SDF.format(source.getBeginAt()));
        }
        if (Objects.nonNull(source.getEndAt())) {
            target.setEndAt(SDF.format(source.getEndAt()));
        }
        target.setDescription(source.getDescription(Locale.getDefault()));
        target.setName(source.getName(Locale.getDefault()));
        if (Objects.nonNull(source.getMedia01())) {
            target.setMedia(mediaConverter.convert(source.getMedia01()));
        }
        target.setType(source.getType());
        target.setOnline(BooleanUtils.toBoolean(source.getOnline()));
        CollectionUtils.emptyIfNull(source.getResources())
                .forEach(resource -> target.addResource(resourceConverter.convert(resource)));
    }

    @Override
    public void reversePopulate(AbstractActivityData source, AbstractActivityModel target) {

    }

}
