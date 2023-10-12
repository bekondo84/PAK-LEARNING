package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class ResourceData implements Serializable {

    private Long pk ;
    private String name;
    private String description ;
    private String filename; ;

    public ResourceData() {
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

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
