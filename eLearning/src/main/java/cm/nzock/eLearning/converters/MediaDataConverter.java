package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.beans.MediaData;
import cm.platform.basecommerce.core.media.MediaModel;
import cm.platform.basecommerce.populator.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MediaDataConverter implements Converter<MediaModel, MediaData> {
    @Override
    public MediaData convert(MediaModel source) {
        final MediaData target = new MediaData();
        target.setCode(source.getCode())
                .setConfidential(Objects.nonNull(source.getConfidential()) ? source.getConfidential():false)
                .setDownloadUrl(source.getDownloadUrl())
                .setFormat(source.getFormat())
                .setImage(source.getImage());

        return target;
    }
}
