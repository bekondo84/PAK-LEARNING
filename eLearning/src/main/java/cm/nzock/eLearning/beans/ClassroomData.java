package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class ClassroomData implements Serializable {
    private String code;
    private String name;
    private Short size;
    private String adresse;

    public ClassroomData() {
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

    public Short getSize() {
        return size;
    }

    public void setSize(Short size) {
        this.size = size;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
