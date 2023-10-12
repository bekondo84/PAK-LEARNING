package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class ThemeData implements Serializable {
    private String code;
    private String name;
    private String resume ;

    public ThemeData() {

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

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
