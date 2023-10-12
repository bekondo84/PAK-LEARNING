package cm.nzock.eLearning.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AbstractActivityData implements Serializable {
    protected String code;
    protected String name ;
    protected String description;
    protected MediaData media ;
    protected String beginAt;
    protected String endAt;
    protected Boolean online ;
    protected String type;

    protected List<ResourceData> resources ;

    public AbstractActivityData() {
        resources = new ArrayList<>();

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaData getMedia() {
        return media;
    }

    public void setMedia(MediaData media) {
        this.media = media;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public List<ResourceData> getResources() {
        return resources;
    }

    public void setResources(List<ResourceData> resources) {
        this.resources = resources;
    }

    public void addResource(final ResourceData resource) {
        resources.add(resource);
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractActivityData data = (AbstractActivityData) o;
        return code.equals(data.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
